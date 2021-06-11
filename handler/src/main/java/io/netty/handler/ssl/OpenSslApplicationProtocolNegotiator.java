package io.netty.handler.ssl;

/**
 * OpenSSL version of {@link ApplicationProtocolNegotiator}.
 *
 * @deprecated use {@link ApplicationProtocolConfig}
 */
@Deprecated
public interface OpenSslApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {

    /**
     * Returns the {@link ApplicationProtocolConfig.Protocol} which should be used.
     */
    ApplicationProtocolConfig.Protocol protocol();

    /**
     * Get the desired behavior for the peer who selects the application protocol.
     */
    ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior();

    /**
     * Get the desired behavior for the peer who is notified of the selected protocol.
     */
    ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior();
}
