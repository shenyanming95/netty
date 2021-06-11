package io.netty.handler.codec.memcache;

import io.netty.util.ReferenceCounted;
import io.netty.util.internal.UnstableApi;

/**
 * Marker interface for both ascii and binary messages.
 */
@UnstableApi
public interface MemcacheMessage extends MemcacheObject, ReferenceCounted {

    /**
     * Increases the reference count by {@code 1}.
     */
    @Override
    MemcacheMessage retain();

    /**
     * Increases the reference count by the specified {@code increment}.
     */
    @Override
    MemcacheMessage retain(int increment);

    @Override
    MemcacheMessage touch();

    @Override
    MemcacheMessage touch(Object hint);
}
