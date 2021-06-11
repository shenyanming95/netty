package io.netty.handler.codec.socksx.v5;

/**
 * A response to a SOCKS5 request detail message, as defined in
 * <a href="http://tools.ietf.org/html/rfc1928#section-6">the section 6, RFC1928</a>.
 */
public interface Socks5CommandResponse extends Socks5Message {

    /**
     * Returns the status of this response.
     */
    Socks5CommandStatus status();

    /**
     * Returns the address type of the {@code BND.ADDR} field of this response.
     */
    Socks5AddressType bndAddrType();

    /**
     * Returns the {@code BND.ADDR} field of this response.
     */
    String bndAddr();

    /**
     * Returns the {@code BND.PORT} field of this response.
     */
    int bndPort();
}
