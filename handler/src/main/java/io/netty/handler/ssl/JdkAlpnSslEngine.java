package io.netty.handler.ssl;

import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SuppressJava6Requirement;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiFunction;

import static io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import static io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import static io.netty.handler.ssl.SslUtils.toSSLHandshakeException;

@SuppressJava6Requirement(reason = "Usage guarded by java version check")
final class JdkAlpnSslEngine extends JdkSslEngine {
    private final ProtocolSelectionListener selectionListener;
    private final AlpnSelector alpnSelector;

    JdkAlpnSslEngine(SSLEngine engine, @SuppressWarnings("deprecation") JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
        super(engine);
        if (isServer) {
            selectionListener = null;
            alpnSelector = new AlpnSelector(applicationNegotiator.protocolSelectorFactory().
                    newSelector(this, new LinkedHashSet<String>(applicationNegotiator.protocols())));
            JdkAlpnSslUtils.setHandshakeApplicationProtocolSelector(engine, alpnSelector);
        } else {
            selectionListener = applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols());
            alpnSelector = null;
            JdkAlpnSslUtils.setApplicationProtocols(engine, applicationNegotiator.protocols());
        }
    }

    private SSLEngineResult verifyProtocolSelection(SSLEngineResult result) throws SSLException {
        if (result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
            if (alpnSelector == null) {
                // This means we are using client-side and
                try {
                    String protocol = getApplicationProtocol();
                    assert protocol != null;
                    if (protocol.isEmpty()) {
                        // If empty the server did not announce ALPN:
                        // See:
                        // http://hg.openjdk.java.net/jdk9/dev/jdk/file/65464a307408/src/java.base/
                        // share/classes/sun/security/ssl/ClientHandshaker.java#l741
                        selectionListener.unsupported();
                    } else {
                        selectionListener.selected(protocol);
                    }
                } catch (Throwable e) {
                    throw toSSLHandshakeException(e);
                }
            } else {
                assert selectionListener == null;
                alpnSelector.checkUnsupported();
            }
        }
        return result;
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        return verifyProtocolSelection(super.wrap(src, dst));
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] srcs, ByteBuffer dst) throws SSLException {
        return verifyProtocolSelection(super.wrap(srcs, dst));
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int len, ByteBuffer dst) throws SSLException {
        return verifyProtocolSelection(super.wrap(srcs, offset, len, dst));
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        return verifyProtocolSelection(super.unwrap(src, dst));
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
        return verifyProtocolSelection(super.unwrap(src, dsts));
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dst, int offset, int len) throws SSLException {
        return verifyProtocolSelection(super.unwrap(src, dst, offset, len));
    }

    @Override
    public String getNegotiatedApplicationProtocol() {
        String protocol = getApplicationProtocol();
        if (protocol != null) {
            return protocol.isEmpty() ? null : protocol;
        }
        return null;
    }

    @Override
    void setNegotiatedApplicationProtocol(String applicationProtocol) {
        // Do nothing as this is handled internally by the Java8u251+ implementation of SSLEngine.
    }

    // These methods will override the methods defined by Java 8u251 and later. As we may compile with an earlier
    // java8 version we don't use @Override annotations here.
    public String getApplicationProtocol() {
        return JdkAlpnSslUtils.getApplicationProtocol(getWrappedEngine());
    }

    public String getHandshakeApplicationProtocol() {
        return JdkAlpnSslUtils.getHandshakeApplicationProtocol(getWrappedEngine());
    }

    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return JdkAlpnSslUtils.getHandshakeApplicationProtocolSelector(getWrappedEngine());
    }

    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> selector) {
        JdkAlpnSslUtils.setHandshakeApplicationProtocolSelector(getWrappedEngine(), selector);
    }

    private final class AlpnSelector implements BiFunction<SSLEngine, List<String>, String> {
        private final ProtocolSelector selector;
        private boolean called;

        AlpnSelector(ProtocolSelector selector) {
            this.selector = selector;
        }

        @Override
        public String apply(SSLEngine sslEngine, List<String> strings) {
            assert !called;
            called = true;

            try {
                String selected = selector.select(strings);
                return selected == null ? StringUtil.EMPTY_STRING : selected;
            } catch (Exception cause) {
                // Returning null means we want to fail the handshake.
                //
                // See http://download.java.net/java/jdk9/docs/api/javax/net/ssl/
                // SSLEngine.html#setHandshakeApplicationProtocolSelector-java.util.function.BiFunction-
                return null;
            }
        }

        void checkUnsupported() {
            if (called) {
                // ALPN message was received by peer and so apply(...) was called.
                // See:
                // http://hg.openjdk.java.net/jdk9/dev/jdk/file/65464a307408/src/
                // java.base/share/classes/sun/security/ssl/ServerHandshaker.java#l933
                return;
            }
            String protocol = getApplicationProtocol();
            assert protocol != null;

            if (protocol.isEmpty()) {
                // ALPN is not supported
                selector.unsupported();
            }
        }
    }
}
