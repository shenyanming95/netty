package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.UnstableApi;

/**
 * Combines {@link MemcacheMessage} and {@link LastMemcacheContent} into one
 * message. So it represent a <i>complete</i> memcache message.
 */
@UnstableApi
public interface FullMemcacheMessage extends MemcacheMessage, LastMemcacheContent {

    @Override
    FullMemcacheMessage copy();

    @Override
    FullMemcacheMessage duplicate();

    @Override
    FullMemcacheMessage retainedDuplicate();

    @Override
    FullMemcacheMessage replace(ByteBuf content);

    @Override
    FullMemcacheMessage retain(int increment);

    @Override
    FullMemcacheMessage retain();

    @Override
    FullMemcacheMessage touch();

    @Override
    FullMemcacheMessage touch(Object hint);
}
