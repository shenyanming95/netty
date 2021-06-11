package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.UnstableApi;

/**
 * The decoder part which takes care of decoding the request-specific headers.
 */
@UnstableApi
public class BinaryMemcacheRequestDecoder extends AbstractBinaryMemcacheDecoder<BinaryMemcacheRequest> {

    public BinaryMemcacheRequestDecoder() {
        this(DEFAULT_MAX_CHUNK_SIZE);
    }

    public BinaryMemcacheRequestDecoder(int chunkSize) {
        super(chunkSize);
    }

    @Override
    protected BinaryMemcacheRequest decodeHeader(ByteBuf in) {
        DefaultBinaryMemcacheRequest header = new DefaultBinaryMemcacheRequest();
        header.setMagic(in.readByte());
        header.setOpcode(in.readByte());
        header.setKeyLength(in.readShort());
        header.setExtrasLength(in.readByte());
        header.setDataType(in.readByte());
        header.setReserved(in.readShort());
        header.setTotalBodyLength(in.readInt());
        header.setOpaque(in.readInt());
        header.setCas(in.readLong());
        return header;
    }

    @Override
    protected BinaryMemcacheRequest buildInvalidMessage() {
        return new DefaultBinaryMemcacheRequest(Unpooled.EMPTY_BUFFER, Unpooled.EMPTY_BUFFER);
    }
}
