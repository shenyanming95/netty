package io.netty.handler.codec.socksx.v5;

/**
 * An initial SOCKS5 authentication method selection request, as defined in
 * <a href="http://tools.ietf.org/html/rfc1928#section-3">the section 3, RFC1928</a>.
 */
public interface Socks5InitialResponse extends Socks5Message {

    /**
     * Returns the {@code METHOD} field of this response.
     */
    Socks5AuthMethod authMethod();
}
