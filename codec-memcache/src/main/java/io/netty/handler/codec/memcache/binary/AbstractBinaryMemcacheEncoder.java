package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectEncoder;
import io.netty.util.internal.UnstableApi;

/**
 * A {@link MessageToByteEncoder} that encodes binary memcache messages into bytes.
 */
@UnstableApi
public abstract class AbstractBinaryMemcacheEncoder<M extends BinaryMemcacheMessage> extends AbstractMemcacheObjectEncoder<M> {

    /**
     * Every binary memcache message has at least a 24 bytes header.
     */
    private static final int MINIMUM_HEADER_SIZE = 24;

    /**
     * Encode the extras.
     *
     * @param buf    the {@link ByteBuf} to write into.
     * @param extras the extras to encode.
     */
    private static void encodeExtras(ByteBuf buf, ByteBuf extras) {
        if (extras == null || !extras.isReadable()) {
            return;
        }

        buf.writeBytes(extras);
    }

    /**
     * Encode the key.
     *
     * @param buf the {@link ByteBuf} to write into.
     * @param key the key to encode.
     */
    private static void encodeKey(ByteBuf buf, ByteBuf key) {
        if (key == null || !key.isReadable()) {
            return;
        }

        buf.writeBytes(key);
    }

    @Override
    protected ByteBuf encodeMessage(ChannelHandlerContext ctx, M msg) {
        ByteBuf buf = ctx.alloc().buffer(MINIMUM_HEADER_SIZE + msg.extrasLength() + msg.keyLength());

        encodeHeader(buf, msg);
        encodeExtras(buf, msg.extras());
        encodeKey(buf, msg.key());

        return buf;
    }

    /**
     * Encode the header.
     * <p/>
     * This methods needs to be implemented by a sub class because the header is different
     * for both requests and responses.
     *
     * @param buf the {@link ByteBuf} to write into.
     * @param msg the message to encode.
     */
    protected abstract void encodeHeader(ByteBuf buf, M msg);

}
