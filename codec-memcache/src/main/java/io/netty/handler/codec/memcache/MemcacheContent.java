package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;
import io.netty.util.internal.UnstableApi;

/**
 * An Memcache content chunk.
 * <p/>
 * A implementation of a {@link AbstractMemcacheObjectDecoder} generates {@link MemcacheContent} after
 * {@link MemcacheMessage} when the content is large. If you prefer not to receive {@link MemcacheContent}
 * in your handler, place a aggregator after an implementation of the {@link AbstractMemcacheObjectDecoder}
 * in the {@link ChannelPipeline}.
 */
@UnstableApi
public interface MemcacheContent extends MemcacheObject, ByteBufHolder {

    @Override
    MemcacheContent copy();

    @Override
    MemcacheContent duplicate();

    @Override
    MemcacheContent retainedDuplicate();

    @Override
    MemcacheContent replace(ByteBuf content);

    @Override
    MemcacheContent retain();

    @Override
    MemcacheContent retain(int increment);

    @Override
    MemcacheContent touch();

    @Override
    MemcacheContent touch(Object hint);
}
