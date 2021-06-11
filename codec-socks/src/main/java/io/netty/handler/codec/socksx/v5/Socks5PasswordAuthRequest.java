package io.netty.handler.codec.socksx.v5;

/**
 * A SOCKS5 subnegotiation request for username-password authentication, as defined in
 * <a href="http://tools.ietf.org/html/rfc1929#section-2">the section 2, RFC1929</a>.
 */
public interface Socks5PasswordAuthRequest extends Socks5Message {

    /**
     * Returns the username of this request.
     */
    String username();

    /**
     * Returns the password of this request.
     */
    String password();
}
