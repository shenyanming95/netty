package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol GOAWAY Frame
 */
public interface SpdyGoAwayFrame extends SpdyFrame {

    /**
     * Returns the Last-good-stream-ID of this frame.
     */
    int lastGoodStreamId();

    /**
     * Sets the Last-good-stream-ID of this frame.  The Last-good-stream-ID
     * cannot be negative.
     */
    SpdyGoAwayFrame setLastGoodStreamId(int lastGoodStreamId);

    /**
     * Returns the status of this frame.
     */
    SpdySessionStatus status();

    /**
     * Sets the status of this frame.
     */
    SpdyGoAwayFrame setStatus(SpdySessionStatus status);
}
