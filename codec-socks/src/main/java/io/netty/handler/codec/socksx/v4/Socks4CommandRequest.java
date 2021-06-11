package io.netty.handler.codec.socksx.v4;

/**
 * A SOCKS4a {@code CONNECT} or {@code BIND} request.
 */
public interface Socks4CommandRequest extends Socks4Message {

    /**
     * Returns the type of this request.
     */
    Socks4CommandType type();

    /**
     * Returns the {@code USERID} field of this request.
     */
    String userId();

    /**
     * Returns the {@code DSTIP} field of this request.
     */
    String dstAddr();

    /**
     * Returns the {@code DSTPORT} field of this request.
     */
    int dstPort();
}
