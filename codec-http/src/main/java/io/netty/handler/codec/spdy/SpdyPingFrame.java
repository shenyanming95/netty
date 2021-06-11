package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol PING Frame
 */
public interface SpdyPingFrame extends SpdyFrame {

    /**
     * Returns the ID of this frame.
     */
    int id();

    /**
     * Sets the ID of this frame.
     */
    SpdyPingFrame setId(int id);
}
