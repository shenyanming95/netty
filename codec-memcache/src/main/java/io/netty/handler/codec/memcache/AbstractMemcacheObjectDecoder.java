package io.netty.handler.codec.memcache;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.UnstableApi;

/**
 * Abstract super class for both ascii and binary decoders.
 * <p/>
 * Currently it just acts as a common denominator, but will certainly include methods once the ascii protocol
 * is implemented.
 */
@UnstableApi
public abstract class AbstractMemcacheObjectDecoder extends ByteToMessageDecoder {
}
