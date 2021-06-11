package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol HEADERS Frame
 */
public interface SpdyHeadersFrame extends SpdyStreamFrame {

    /**
     * Returns {@code true} if this header block is invalid.
     * A RST_STREAM frame with code PROTOCOL_ERROR should be sent.
     */
    boolean isInvalid();

    /**
     * Marks this header block as invalid.
     */
    SpdyHeadersFrame setInvalid();

    /**
     * Returns {@code true} if this header block has been truncated due to
     * length restrictions.
     */
    boolean isTruncated();

    /**
     * Mark this header block as truncated.
     */
    SpdyHeadersFrame setTruncated();

    /**
     * Returns the {@link SpdyHeaders}.
     */
    SpdyHeaders headers();

    @Override
    SpdyHeadersFrame setStreamId(int streamID);

    @Override
    SpdyHeadersFrame setLast(boolean last);
}
