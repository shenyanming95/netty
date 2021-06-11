package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;

/**
 * A SPDY Protocol DATA Frame
 */
public interface SpdyDataFrame extends ByteBufHolder, SpdyStreamFrame {

    @Override
    SpdyDataFrame setStreamId(int streamID);

    @Override
    SpdyDataFrame setLast(boolean last);

    /**
     * Returns the data payload of this frame.  If there is no data payload
     * {@link Unpooled#EMPTY_BUFFER} is returned.
     * <p>
     * The data payload cannot exceed 16777215 bytes.
     */
    @Override
    ByteBuf content();

    @Override
    SpdyDataFrame copy();

    @Override
    SpdyDataFrame duplicate();

    @Override
    SpdyDataFrame retainedDuplicate();

    @Override
    SpdyDataFrame replace(ByteBuf content);

    @Override
    SpdyDataFrame retain();

    @Override
    SpdyDataFrame retain(int increment);

    @Override
    SpdyDataFrame touch();

    @Override
    SpdyDataFrame touch(Object hint);
}
