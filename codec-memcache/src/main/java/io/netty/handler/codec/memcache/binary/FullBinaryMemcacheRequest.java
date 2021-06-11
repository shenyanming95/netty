package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;
import io.netty.util.internal.UnstableApi;

/**
 * A {@link BinaryMemcacheRequest} that also includes the content.
 */
@UnstableApi
public interface FullBinaryMemcacheRequest extends BinaryMemcacheRequest, FullMemcacheMessage {

    @Override
    FullBinaryMemcacheRequest copy();

    @Override
    FullBinaryMemcacheRequest duplicate();

    @Override
    FullBinaryMemcacheRequest retainedDuplicate();

    @Override
    FullBinaryMemcacheRequest replace(ByteBuf content);

    @Override
    FullBinaryMemcacheRequest retain(int increment);

    @Override
    FullBinaryMemcacheRequest retain();

    @Override
    FullBinaryMemcacheRequest touch();

    @Override
    FullBinaryMemcacheRequest touch(Object hint);
}
