package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Compresses a {@link ByteBuf} using the deflate algorithm.
 */
public abstract class ZlibEncoder extends MessageToByteEncoder<ByteBuf> {

    protected ZlibEncoder() {
        super(false);
    }

    /**
     * Returns {@code true} if and only if the end of the compressed stream
     * has been reached.
     */
    public abstract boolean isClosed();

    /**
     * Close this {@link ZlibEncoder} and so finish the encoding.
     * <p>
     * The returned {@link ChannelFuture} will be notified once the
     * operation completes.
     */
    public abstract ChannelFuture close();

    /**
     * Close this {@link ZlibEncoder} and so finish the encoding.
     * The given {@link ChannelFuture} will be notified once the operation
     * completes and will also be returned.
     */
    public abstract ChannelFuture close(ChannelPromise promise);

}
