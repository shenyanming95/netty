package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

/**
 * The default {@link SpdyGoAwayFrame} implementation.
 */
public class DefaultSpdyGoAwayFrame implements SpdyGoAwayFrame {

    private int lastGoodStreamId;
    private SpdySessionStatus status;

    /**
     * Creates a new instance.
     *
     * @param lastGoodStreamId the Last-good-stream-ID of this frame
     */
    public DefaultSpdyGoAwayFrame(int lastGoodStreamId) {
        this(lastGoodStreamId, 0);
    }

    /**
     * Creates a new instance.
     *
     * @param lastGoodStreamId the Last-good-stream-ID of this frame
     * @param statusCode       the Status code of this frame
     */
    public DefaultSpdyGoAwayFrame(int lastGoodStreamId, int statusCode) {
        this(lastGoodStreamId, SpdySessionStatus.valueOf(statusCode));
    }

    /**
     * Creates a new instance.
     *
     * @param lastGoodStreamId the Last-good-stream-ID of this frame
     * @param status           the status of this frame
     */
    public DefaultSpdyGoAwayFrame(int lastGoodStreamId, SpdySessionStatus status) {
        setLastGoodStreamId(lastGoodStreamId);
        setStatus(status);
    }

    @Override
    public int lastGoodStreamId() {
        return lastGoodStreamId;
    }

    @Override
    public SpdyGoAwayFrame setLastGoodStreamId(int lastGoodStreamId) {
        checkPositiveOrZero(lastGoodStreamId, "lastGoodStreamId");
        this.lastGoodStreamId = lastGoodStreamId;
        return this;
    }

    @Override
    public SpdySessionStatus status() {
        return status;
    }

    @Override
    public SpdyGoAwayFrame setStatus(SpdySessionStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(StringUtil.simpleClassName(this)).append(StringUtil.NEWLINE).append("--> Last-good-stream-ID = ").append(lastGoodStreamId()).append(StringUtil.NEWLINE).append("--> Status: ").append(status()).toString();
    }
}
