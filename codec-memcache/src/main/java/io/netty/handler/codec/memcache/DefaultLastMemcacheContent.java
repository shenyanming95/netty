package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.UnstableApi;

/**
 * The default implementation for the {@link LastMemcacheContent}.
 */
@UnstableApi
public class DefaultLastMemcacheContent extends DefaultMemcacheContent implements LastMemcacheContent {

    public DefaultLastMemcacheContent() {
        super(Unpooled.buffer());
    }

    public DefaultLastMemcacheContent(ByteBuf content) {
        super(content);
    }

    @Override
    public LastMemcacheContent retain() {
        super.retain();
        return this;
    }

    @Override
    public LastMemcacheContent retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public LastMemcacheContent touch() {
        super.touch();
        return this;
    }

    @Override
    public LastMemcacheContent touch(Object hint) {
        super.touch(hint);
        return this;
    }

    @Override
    public LastMemcacheContent copy() {
        return replace(content().copy());
    }

    @Override
    public LastMemcacheContent duplicate() {
        return replace(content().duplicate());
    }

    @Override
    public LastMemcacheContent retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override
    public LastMemcacheContent replace(ByteBuf content) {
        return new DefaultLastMemcacheContent(content);
    }
}
