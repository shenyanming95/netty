package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol RST_STREAM Frame
 */
public interface SpdyRstStreamFrame extends SpdyStreamFrame {

    /**
     * Returns the status of this frame.
     */
    SpdyStreamStatus status();

    /**
     * Sets the status of this frame.
     */
    SpdyRstStreamFrame setStatus(SpdyStreamStatus status);

    @Override
    SpdyRstStreamFrame setStreamId(int streamId);

    @Override
    SpdyRstStreamFrame setLast(boolean last);
}
