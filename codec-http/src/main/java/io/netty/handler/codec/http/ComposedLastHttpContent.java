package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;


final class ComposedLastHttpContent implements LastHttpContent {
    private final HttpHeaders trailingHeaders;
    private DecoderResult result;

    ComposedLastHttpContent(HttpHeaders trailingHeaders) {
        this.trailingHeaders = trailingHeaders;
    }

    ComposedLastHttpContent(HttpHeaders trailingHeaders, DecoderResult result) {
        this(trailingHeaders);
        this.result = result;
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return trailingHeaders;
    }

    @Override
    public LastHttpContent copy() {
        LastHttpContent content = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        content.trailingHeaders().set(trailingHeaders());
        return content;
    }

    @Override
    public LastHttpContent duplicate() {
        return copy();
    }

    @Override
    public LastHttpContent retainedDuplicate() {
        return copy();
    }

    @Override
    public LastHttpContent replace(ByteBuf content) {
        final LastHttpContent dup = new DefaultLastHttpContent(content);
        dup.trailingHeaders().setAll(trailingHeaders());
        return dup;
    }

    @Override
    public LastHttpContent retain(int increment) {
        return this;
    }

    @Override
    public LastHttpContent retain() {
        return this;
    }

    @Override
    public LastHttpContent touch() {
        return this;
    }

    @Override
    public LastHttpContent touch(Object hint) {
        return this;
    }

    @Override
    public ByteBuf content() {
        return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public DecoderResult decoderResult() {
        return result;
    }

    @Override
    public DecoderResult getDecoderResult() {
        return decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult result) {
        this.result = result;
    }

    @Override
    public int refCnt() {
        return 1;
    }

    @Override
    public boolean release() {
        return false;
    }

    @Override
    public boolean release(int decrement) {
        return false;
    }
}
