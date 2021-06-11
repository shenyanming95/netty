package io.netty.handler.codec.memcache;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.UnstableApi;

/**
 * The default {@link MemcacheObject} implementation.
 */
@UnstableApi
public abstract class AbstractMemcacheObject extends AbstractReferenceCounted implements MemcacheObject {

    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    protected AbstractMemcacheObject() {
        // Disallow direct instantiation
    }

    @Override
    public DecoderResult decoderResult() {
        return decoderResult;
    }

    @Override
    public void setDecoderResult(DecoderResult result) {
        this.decoderResult = ObjectUtil.checkNotNull(result, "DecoderResult should not be null.");
    }
}
