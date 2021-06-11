package io.netty.buffer;

/**
 * A derived buffer which exposes its parent's sub-region only.  It is
 * recommended to use {@link ByteBuf#slice()} and
 * {@link ByteBuf#slice(int, int)} instead of calling the constructor
 * explicitly.
 *
 * @deprecated Do not use.
 */
@Deprecated
public class SlicedByteBuf extends AbstractUnpooledSlicedByteBuf {

    private int length;

    public SlicedByteBuf(ByteBuf buffer, int index, int length) {
        super(buffer, index, length);
    }

    @Override
    final void initLength(int length) {
        this.length = length;
    }

    @Override
    final int length() {
        return length;
    }

    @Override
    public int capacity() {
        return length;
    }
}
