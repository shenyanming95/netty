package io.netty.handler.codec.memcache.binary;

import io.netty.util.internal.UnstableApi;

/**
 * Represents a full {@link BinaryMemcacheResponse}, which contains the header and optional key and extras.
 */
@UnstableApi
public interface BinaryMemcacheResponse extends BinaryMemcacheMessage {

    /**
     * Returns the status of the response.
     *
     * @return the status of the response.
     */
    short status();

    /**
     * Sets the status of the response.
     *
     * @param status the status to set.
     */
    BinaryMemcacheResponse setStatus(short status);

    @Override
    BinaryMemcacheResponse retain();

    @Override
    BinaryMemcacheResponse retain(int increment);

    @Override
    BinaryMemcacheResponse touch();

    @Override
    BinaryMemcacheResponse touch(Object hint);
}
