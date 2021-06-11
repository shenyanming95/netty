package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/**
 * The default {@link SpdyRstStreamFrame} implementation.
 */
public class DefaultSpdyRstStreamFrame extends DefaultSpdyStreamFrame implements SpdyRstStreamFrame {

    private SpdyStreamStatus status;

    /**
     * Creates a new instance.
     *
     * @param streamId   the Stream-ID of this frame
     * @param statusCode the Status code of this frame
     */
    public DefaultSpdyRstStreamFrame(int streamId, int statusCode) {
        this(streamId, SpdyStreamStatus.valueOf(statusCode));
    }

    /**
     * Creates a new instance.
     *
     * @param streamId the Stream-ID of this frame
     * @param status   the status of this frame
     */
    public DefaultSpdyRstStreamFrame(int streamId, SpdyStreamStatus status) {
        super(streamId);
        setStatus(status);
    }

    @Override
    public SpdyRstStreamFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    @Override
    public SpdyRstStreamFrame setLast(boolean last) {
        super.setLast(last);
        return this;
    }

    @Override
    public SpdyStreamStatus status() {
        return status;
    }

    @Override
    public SpdyRstStreamFrame setStatus(SpdyStreamStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(StringUtil.simpleClassName(this)).append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(streamId()).append(StringUtil.NEWLINE).append("--> Status: ").append(status()).toString();
    }
}
