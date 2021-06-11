package io.netty.handler.ssl;

import java.util.List;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * OpenSSL {@link ApplicationProtocolNegotiator} for ALPN and NPN.
 *
 * @deprecated use {@link ApplicationProtocolConfig}.
 */
@Deprecated
public final class OpenSslDefaultApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final ApplicationProtocolConfig config;

    public OpenSslDefaultApplicationProtocolNegotiator(ApplicationProtocolConfig config) {
        this.config = checkNotNull(config, "config");
    }

    @Override
    public List<String> protocols() {
        return config.supportedProtocols();
    }

    @Override
    public ApplicationProtocolConfig.Protocol protocol() {
        return config.protocol();
    }

    @Override
    public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
        return config.selectorFailureBehavior();
    }

    @Override
    public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return config.selectedListenerFailureBehavior();
    }
}
