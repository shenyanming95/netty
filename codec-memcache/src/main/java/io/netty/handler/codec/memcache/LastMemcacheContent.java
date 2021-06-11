package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.UnstableApi;

/**
 * The {@link MemcacheContent} which signals the end of the content batch.
 * <p/>
 * Note that by design, even when no content is emitted by the protocol, an
 * empty {@link LastMemcacheContent} is issued to make the upstream parsing
 * easier.
 */
@UnstableApi
public interface LastMemcacheContent extends MemcacheContent {

    LastMemcacheContent EMPTY_LAST_CONTENT = new LastMemcacheContent() {

        @Override
        public LastMemcacheContent copy() {
            return EMPTY_LAST_CONTENT;
        }

        @Override
        public LastMemcacheContent duplicate() {
            return this;
        }

        @Override
        public LastMemcacheContent retainedDuplicate() {
            return this;
        }

        @Override
        public LastMemcacheContent replace(ByteBuf content) {
            return new DefaultLastMemcacheContent(content);
        }

        @Override
        public LastMemcacheContent retain(int increment) {
            return this;
        }

        @Override
        public LastMemcacheContent retain() {
            return this;
        }

        @Override
        public LastMemcacheContent touch() {
            return this;
        }

        @Override
        public LastMemcacheContent touch(Object hint) {
            return this;
        }

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override
        public DecoderResult decoderResult() {
            return DecoderResult.SUCCESS;
        }

        @Override
        public void setDecoderResult(DecoderResult result) {
            throw new UnsupportedOperationException("read only");
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
    };

    @Override
    LastMemcacheContent copy();

    @Override
    LastMemcacheContent duplicate();

    @Override
    LastMemcacheContent retainedDuplicate();

    @Override
    LastMemcacheContent replace(ByteBuf content);

    @Override
    LastMemcacheContent retain(int increment);

    @Override
    LastMemcacheContent retain();

    @Override
    LastMemcacheContent touch();

    @Override
    LastMemcacheContent touch(Object hint);
}
