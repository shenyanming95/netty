package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol Frame that is associated with an individual SPDY Stream
 */
public interface SpdyStreamFrame extends SpdyFrame {

    /**
     * Returns the Stream-ID of this frame.
     */
    int streamId();

    /**
     * Sets the Stream-ID of this frame.  The Stream-ID must be positive.
     */
    SpdyStreamFrame setStreamId(int streamID);

    /**
     * Returns {@code true} if this frame is the last frame to be transmitted
     * on the stream.
     */
    boolean isLast();

    /**
     * Sets if this frame is the last frame to be transmitted on the stream.
     */
    SpdyStreamFrame setLast(boolean last);
}
