package io.netty.handler.ssl;

import java.util.List;

/**
 * Interface to support Application Protocol Negotiation.
 * <p>
 * Default implementations are provided for:
 * <ul>
 * <li><a href="https://technotes.googlecode.com/git/nextprotoneg.html">Next Protocol Negotiation</a></li>
 * <li><a href="http://tools.ietf.org/html/rfc7301">Application-Layer Protocol Negotiation</a></li>
 * </ul>
 *
 * @deprecated use {@link ApplicationProtocolConfig}
 */
@SuppressWarnings("deprecation")
public interface ApplicationProtocolNegotiator {
    /**
     * Get the collection of application protocols supported by this application (in preference order).
     */
    List<String> protocols();
}
