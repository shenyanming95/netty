package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.UnstableApi;

/**
 * The default {@link MemcacheContent} implementation.
 */
@UnstableApi
public class DefaultMemcacheContent extends AbstractMemcacheObject implements MemcacheContent {

    private final ByteBuf content;

    /**
     * Creates a new instance with the specified content.
     */
    public DefaultMemcacheContent(ByteBuf content) {
        this.content = ObjectUtil.checkNotNull(content, "content");
    }

    @Override
    public ByteBuf content() {
        return content;
    }

    @Override
    public MemcacheContent copy() {
        return replace(content.copy());
    }

    @Override
    public MemcacheContent duplicate() {
        return replace(content.duplicate());
    }

    @Override
    public MemcacheContent retainedDuplicate() {
        return replace(content.retainedDuplicate());
    }

    @Override
    public MemcacheContent replace(ByteBuf content) {
        return new DefaultMemcacheContent(content);
    }

    @Override
    public MemcacheContent retain() {
        super.retain();
        return this;
    }

    @Override
    public MemcacheContent retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public MemcacheContent touch() {
        super.touch();
        return this;
    }

    @Override
    public MemcacheContent touch(Object hint) {
        content.touch(hint);
        return this;
    }

    @Override
    protected void deallocate() {
        content.release();
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(data: " + content() + ", decoderResult: " + decoderResult() + ')';
    }
}
