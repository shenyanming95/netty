package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.internal.UnstableApi;

@UnstableApi
public interface Http2UnknownFrame extends Http2StreamFrame, ByteBufHolder {

    @Override
    Http2FrameStream stream();

    @Override
    Http2UnknownFrame stream(Http2FrameStream stream);

    byte frameType();

    Http2Flags flags();

    @Override
    Http2UnknownFrame copy();

    @Override
    Http2UnknownFrame duplicate();

    @Override
    Http2UnknownFrame retainedDuplicate();

    @Override
    Http2UnknownFrame replace(ByteBuf content);

    @Override
    Http2UnknownFrame retain();

    @Override
    Http2UnknownFrame retain(int increment);

    @Override
    Http2UnknownFrame touch();

    @Override
    Http2UnknownFrame touch(Object hint);
}
