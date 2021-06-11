package io.netty.handler.ssl;

import java.util.List;

import static io.netty.handler.ssl.ApplicationProtocolUtil.toList;
import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * OpenSSL {@link ApplicationProtocolNegotiator} for NPN.
 *
 * @deprecated use {@link ApplicationProtocolConfig}
 */
@Deprecated
public final class OpenSslNpnApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final List<String> protocols;

    public OpenSslNpnApplicationProtocolNegotiator(Iterable<String> protocols) {
        this.protocols = checkNotNull(toList(protocols), "protocols");
    }

    public OpenSslNpnApplicationProtocolNegotiator(String... protocols) {
        this.protocols = checkNotNull(toList(protocols), "protocols");
    }

    @Override
    public ApplicationProtocolConfig.Protocol protocol() {
        return ApplicationProtocolConfig.Protocol.NPN;
    }

    @Override
    public List<String> protocols() {
        return protocols;
    }

    @Override
    public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
        return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
    }

    @Override
    public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
    }
}
