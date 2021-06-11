package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;
import io.netty.util.internal.UnstableApi;

/**
 * A {@link BinaryMemcacheResponse} that also includes the content.
 */
@UnstableApi
public interface FullBinaryMemcacheResponse extends BinaryMemcacheResponse, FullMemcacheMessage {

    @Override
    FullBinaryMemcacheResponse copy();

    @Override
    FullBinaryMemcacheResponse duplicate();

    @Override
    FullBinaryMemcacheResponse retainedDuplicate();

    @Override
    FullBinaryMemcacheResponse replace(ByteBuf content);

    @Override
    FullBinaryMemcacheResponse retain(int increment);

    @Override
    FullBinaryMemcacheResponse retain();

    @Override
    FullBinaryMemcacheResponse touch();

    @Override
    FullBinaryMemcacheResponse touch(Object hint);
}
