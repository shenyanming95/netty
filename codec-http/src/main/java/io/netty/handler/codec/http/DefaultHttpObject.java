package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.ObjectUtil;

public class DefaultHttpObject implements HttpObject {

    private static final int HASH_CODE_PRIME = 31;
    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    protected DefaultHttpObject() {
        // Disallow direct instantiation
    }

    @Override
    public DecoderResult decoderResult() {
        return decoderResult;
    }

    @Override
    @Deprecated
    public DecoderResult getDecoderResult() {
        return decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        this.decoderResult = ObjectUtil.checkNotNull(decoderResult, "decoderResult");
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = HASH_CODE_PRIME * result + decoderResult.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttpObject)) {
            return false;
        }

        DefaultHttpObject other = (DefaultHttpObject) o;

        return decoderResult().equals(other.decoderResult());
    }
}
