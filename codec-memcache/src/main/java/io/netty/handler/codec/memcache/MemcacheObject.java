package io.netty.handler.codec.memcache;

import io.netty.handler.codec.DecoderResultProvider;
import io.netty.util.internal.UnstableApi;

/**
 * Defines a common interface for all {@link MemcacheObject} implementations.
 */
@UnstableApi
public interface MemcacheObject extends DecoderResultProvider {
}
