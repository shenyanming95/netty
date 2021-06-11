package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

import static io.netty.util.internal.ObjectUtil.checkPositive;
import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

/**
 * The default {@link SpdyWindowUpdateFrame} implementation.
 */
public class DefaultSpdyWindowUpdateFrame implements SpdyWindowUpdateFrame {

    private int streamId;
    private int deltaWindowSize;

    /**
     * Creates a new instance.
     *
     * @param streamId        the Stream-ID of this frame
     * @param deltaWindowSize the Delta-Window-Size of this frame
     */
    public DefaultSpdyWindowUpdateFrame(int streamId, int deltaWindowSize) {
        setStreamId(streamId);
        setDeltaWindowSize(deltaWindowSize);
    }

    @Override
    public int streamId() {
        return streamId;
    }

    @Override
    public SpdyWindowUpdateFrame setStreamId(int streamId) {
        checkPositiveOrZero(streamId, "streamId");
        this.streamId = streamId;
        return this;
    }

    @Override
    public int deltaWindowSize() {
        return deltaWindowSize;
    }

    @Override
    public SpdyWindowUpdateFrame setDeltaWindowSize(int deltaWindowSize) {
        checkPositive(deltaWindowSize, "deltaWindowSize");
        this.deltaWindowSize = deltaWindowSize;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(StringUtil.simpleClassName(this)).append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(streamId()).append(StringUtil.NEWLINE).append("--> Delta-Window-Size = ").append(deltaWindowSize()).toString();
    }
}
