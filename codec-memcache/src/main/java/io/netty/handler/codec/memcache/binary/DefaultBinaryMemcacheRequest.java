package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.UnstableApi;

/**
 * The default implementation of the {@link BinaryMemcacheRequest}.
 */
@UnstableApi
public class DefaultBinaryMemcacheRequest extends AbstractBinaryMemcacheMessage implements BinaryMemcacheRequest {

    /**
     * Default magic byte for a request.
     */
    public static final byte REQUEST_MAGIC_BYTE = (byte) 0x80;

    private short reserved;

    /**
     * Create a new {@link DefaultBinaryMemcacheRequest} with the header only.
     */
    public DefaultBinaryMemcacheRequest() {
        this(null, null);
    }

    /**
     * Create a new {@link DefaultBinaryMemcacheRequest} with the header and key.
     *
     * @param key the key to use.
     */
    public DefaultBinaryMemcacheRequest(ByteBuf key) {
        this(key, null);
    }

    /**
     * Create a new {@link DefaultBinaryMemcacheRequest} with the header only.
     *
     * @param key    the key to use.
     * @param extras the extras to use.
     */
    public DefaultBinaryMemcacheRequest(ByteBuf key, ByteBuf extras) {
        super(key, extras);
        setMagic(REQUEST_MAGIC_BYTE);
    }

    @Override
    public short reserved() {
        return reserved;
    }

    @Override
    public BinaryMemcacheRequest setReserved(short reserved) {
        this.reserved = reserved;
        return this;
    }

    @Override
    public BinaryMemcacheRequest retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryMemcacheRequest retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public BinaryMemcacheRequest touch() {
        super.touch();
        return this;
    }

    @Override
    public BinaryMemcacheRequest touch(Object hint) {
        super.touch(hint);
        return this;
    }

    /**
     * Copies special metadata hold by this instance to the provided instance
     *
     * @param dst The instance where to copy the metadata of this instance to
     */
    void copyMeta(DefaultBinaryMemcacheRequest dst) {
        super.copyMeta(dst);
        dst.reserved = reserved;
    }
}
