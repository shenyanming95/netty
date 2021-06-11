package io.netty.handler.codec.socksx.v5;

/**
 * A SOCKS5 subnegotiation response for username-password authentication, as defined in
 * <a href="http://tools.ietf.org/html/rfc1929#section-2">the section 2, RFC1929</a>.
 */
public interface Socks5PasswordAuthResponse extends Socks5Message {
    /**
     * Returns the status of this response.
     */
    Socks5PasswordAuthStatus status();
}
