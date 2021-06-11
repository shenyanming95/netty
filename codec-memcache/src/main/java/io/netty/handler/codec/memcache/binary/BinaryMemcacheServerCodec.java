package io.netty.handler.codec.memcache.binary;

import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.util.internal.UnstableApi;

/**
 * The full server codec that combines the correct encoder and decoder.
 * <p/>
 * Use this codec if you need to implement a server that speaks the memcache binary protocol.
 * Internally, it combines the {@link BinaryMemcacheRequestDecoder} and the
 * {@link BinaryMemcacheResponseEncoder} to request decoding and response encoding.
 */
@UnstableApi
public class BinaryMemcacheServerCodec extends CombinedChannelDuplexHandler<BinaryMemcacheRequestDecoder, BinaryMemcacheResponseEncoder> {

    public BinaryMemcacheServerCodec() {
        this(AbstractBinaryMemcacheDecoder.DEFAULT_MAX_CHUNK_SIZE);
    }

    public BinaryMemcacheServerCodec(int decodeChunkSize) {
        super(new BinaryMemcacheRequestDecoder(decodeChunkSize), new BinaryMemcacheResponseEncoder());
    }
}
