package io.netty.handler.ssl;

import javax.net.ssl.SSLEngine;
import java.util.Collections;
import java.util.List;

/**
 * The {@link JdkApplicationProtocolNegotiator} to use if you do not care about NPN or ALPN and are using
 * {@link SslProvider#JDK}.
 */
final class JdkDefaultApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
    public static final JdkDefaultApplicationProtocolNegotiator INSTANCE = new JdkDefaultApplicationProtocolNegotiator();
    private static final SslEngineWrapperFactory DEFAULT_SSL_ENGINE_WRAPPER_FACTORY = new SslEngineWrapperFactory() {
        @Override
        public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
            return engine;
        }
    };

    private JdkDefaultApplicationProtocolNegotiator() {
    }

    @Override
    public SslEngineWrapperFactory wrapperFactory() {
        return DEFAULT_SSL_ENGINE_WRAPPER_FACTORY;
    }

    @Override
    public ProtocolSelectorFactory protocolSelectorFactory() {
        throw new UnsupportedOperationException("Application protocol negotiation unsupported");
    }

    @Override
    public ProtocolSelectionListenerFactory protocolListenerFactory() {
        throw new UnsupportedOperationException("Application protocol negotiation unsupported");
    }

    @Override
    public List<String> protocols() {
        return Collections.emptyList();
    }
}
