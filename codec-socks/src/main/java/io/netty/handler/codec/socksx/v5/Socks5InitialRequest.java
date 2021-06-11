package io.netty.handler.codec.socksx.v5;

import java.util.List;

/**
 * An initial SOCKS5 authentication method selection request, as defined in
 * <a href="http://tools.ietf.org/html/rfc1928#section-3">the section 3, RFC1928</a>.
 */
public interface Socks5InitialRequest extends Socks5Message {
    /**
     * Returns the list of desired authentication methods.
     */
    List<Socks5AuthMethod> authMethods();
}
