package io.netty.handler.codec.spdy;

import static io.netty.util.internal.ObjectUtil.checkPositive;

/**
 * The default {@link SpdyStreamFrame} implementation.
 */
public abstract class DefaultSpdyStreamFrame implements SpdyStreamFrame {

    private int streamId;
    private boolean last;

    /**
     * Creates a new instance.
     *
     * @param streamId the Stream-ID of this frame
     */
    protected DefaultSpdyStreamFrame(int streamId) {
        setStreamId(streamId);
    }

    @Override
    public int streamId() {
        return streamId;
    }

    @Override
    public SpdyStreamFrame setStreamId(int streamId) {
        checkPositive(streamId, "streamId");
        this.streamId = streamId;
        return this;
    }

    @Override
    public boolean isLast() {
        return last;
    }

    @Override
    public SpdyStreamFrame setLast(boolean last) {
        this.last = last;
        return this;
    }
}
