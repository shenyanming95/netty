package io.netty.handler.codec.memcache.binary;

import io.netty.util.internal.UnstableApi;

/**
 * Represents a full {@link BinaryMemcacheRequest}, which contains the header and optional key and extras.
 */
@UnstableApi
public interface BinaryMemcacheRequest extends BinaryMemcacheMessage {

    /**
     * Returns the reserved field value.
     *
     * @return the reserved field value.
     */
    short reserved();

    /**
     * Sets the reserved field value.
     *
     * @param reserved the reserved field value.
     */
    BinaryMemcacheRequest setReserved(short reserved);

    @Override
    BinaryMemcacheRequest retain();

    @Override
    BinaryMemcacheRequest retain(int increment);

    @Override
    BinaryMemcacheRequest touch();

    @Override
    BinaryMemcacheRequest touch(Object hint);
}
