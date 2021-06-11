package io.netty.buffer;

import java.nio.ByteBuffer;

/**
 * Abstract base class for {@link ByteBuf} implementations that wrap another
 * {@link ByteBuf}.
 *
 * @deprecated Do not use.
 */
@Deprecated
public abstract class AbstractDerivedByteBuf extends AbstractByteBuf {

    protected AbstractDerivedByteBuf(int maxCapacity) {
        super(maxCapacity);
    }

    @Override
    final boolean isAccessible() {
        return unwrap().isAccessible();
    }

    @Override
    public final int refCnt() {
        return refCnt0();
    }

    int refCnt0() {
        return unwrap().refCnt();
    }

    @Override
    public final ByteBuf retain() {
        return retain0();
    }

    ByteBuf retain0() {
        unwrap().retain();
        return this;
    }

    @Override
    public final ByteBuf retain(int increment) {
        return retain0(increment);
    }

    ByteBuf retain0(int increment) {
        unwrap().retain(increment);
        return this;
    }

    @Override
    public final ByteBuf touch() {
        return touch0();
    }

    ByteBuf touch0() {
        unwrap().touch();
        return this;
    }

    @Override
    public final ByteBuf touch(Object hint) {
        return touch0(hint);
    }

    ByteBuf touch0(Object hint) {
        unwrap().touch(hint);
        return this;
    }

    @Override
    public final boolean release() {
        return release0();
    }

    boolean release0() {
        return unwrap().release();
    }

    @Override
    public final boolean release(int decrement) {
        return release0(decrement);
    }

    boolean release0(int decrement) {
        return unwrap().release(decrement);
    }

    @Override
    public boolean isReadOnly() {
        return unwrap().isReadOnly();
    }

    @Override
    public ByteBuffer internalNioBuffer(int index, int length) {
        return nioBuffer(index, length);
    }

    @Override
    public ByteBuffer nioBuffer(int index, int length) {
        return unwrap().nioBuffer(index, length);
    }

    @Override
    public boolean isContiguous() {
        return unwrap().isContiguous();
    }
}
