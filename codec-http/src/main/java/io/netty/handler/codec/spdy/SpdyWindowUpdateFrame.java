package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol WINDOW_UPDATE Frame
 */
public interface SpdyWindowUpdateFrame extends SpdyFrame {

    /**
     * Returns the Stream-ID of this frame.
     */
    int streamId();

    /**
     * Sets the Stream-ID of this frame.  The Stream-ID cannot be negative.
     */
    SpdyWindowUpdateFrame setStreamId(int streamID);

    /**
     * Returns the Delta-Window-Size of this frame.
     */
    int deltaWindowSize();

    /**
     * Sets the Delta-Window-Size of this frame.
     * The Delta-Window-Size must be positive.
     */
    SpdyWindowUpdateFrame setDeltaWindowSize(int deltaWindowSize);
}
