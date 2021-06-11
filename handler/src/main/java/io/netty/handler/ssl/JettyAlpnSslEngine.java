package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import io.netty.util.internal.PlatformDependent;
import org.eclipse.jetty.alpn.ALPN;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import java.util.LinkedHashSet;
import java.util.List;

import static io.netty.handler.ssl.SslUtils.toSSLHandshakeException;
import static io.netty.util.internal.ObjectUtil.checkNotNull;

abstract class JettyAlpnSslEngine extends JdkSslEngine {
    private static final boolean available = initAvailable();

    private JettyAlpnSslEngine(SSLEngine engine) {
        super(engine);
    }

    static boolean isAvailable() {
        return available;
    }

    private static boolean initAvailable() {
        if (PlatformDependent.javaVersion() <= 8) {
            try {
                // Always use bootstrap class loader.
                Class.forName("sun.security.ssl.ALPNExtension", true, null);
                return true;
            } catch (Throwable ignore) {
                // alpn-boot was not loaded.
            }
        }
        return false;
    }

    static JettyAlpnSslEngine newClientEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
        return new ClientEngine(engine, applicationNegotiator);
    }

    static JettyAlpnSslEngine newServerEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
        return new ServerEngine(engine, applicationNegotiator);
    }

    private static final class ClientEngine extends JettyAlpnSslEngine {
        ClientEngine(SSLEngine engine, final JdkApplicationProtocolNegotiator applicationNegotiator) {
            super(engine);
            checkNotNull(applicationNegotiator, "applicationNegotiator");
            final ProtocolSelectionListener protocolListener = checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
            ALPN.put(engine, new ALPN.ClientProvider() {
                @Override
                public List<String> protocols() {
                    return applicationNegotiator.protocols();
                }

                @Override
                public void selected(String protocol) throws SSLException {
                    try {
                        protocolListener.selected(protocol);
                    } catch (Throwable t) {
                        throw toSSLHandshakeException(t);
                    }
                }

                @Override
                public void unsupported() {
                    protocolListener.unsupported();
                }
            });
        }

        @Override
        public void closeInbound() throws SSLException {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeInbound();
            }
        }

        @Override
        public void closeOutbound() {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeOutbound();
            }
        }
    }

    private static final class ServerEngine extends JettyAlpnSslEngine {
        ServerEngine(SSLEngine engine, final JdkApplicationProtocolNegotiator applicationNegotiator) {
            super(engine);
            checkNotNull(applicationNegotiator, "applicationNegotiator");
            final ProtocolSelector protocolSelector = checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet<String>(applicationNegotiator.protocols())), "protocolSelector");
            ALPN.put(engine, new ALPN.ServerProvider() {
                @Override
                public String select(List<String> protocols) throws SSLException {
                    try {
                        return protocolSelector.select(protocols);
                    } catch (Throwable t) {
                        throw toSSLHandshakeException(t);
                    }
                }

                @Override
                public void unsupported() {
                    protocolSelector.unsupported();
                }
            });
        }

        @Override
        public void closeInbound() throws SSLException {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeInbound();
            }
        }

        @Override
        public void closeOutbound() {
            try {
                ALPN.remove(getWrappedEngine());
            } finally {
                super.closeOutbound();
            }
        }
    }
}
