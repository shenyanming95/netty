package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encodes an {@link SocksMessage} into a {@link ByteBuf}.
 * {@link MessageToByteEncoder} implementation.
 * Use this with {@link SocksInitRequest}, {@link SocksInitResponse}, {@link SocksAuthRequest},
 * {@link SocksAuthResponse}, {@link SocksCmdRequest} and {@link SocksCmdResponse}
 */
@ChannelHandler.Sharable
public class SocksMessageEncoder extends MessageToByteEncoder<SocksMessage> {
    @Override
    @SuppressWarnings("deprecation")
    protected void encode(ChannelHandlerContext ctx, SocksMessage msg, ByteBuf out) throws Exception {
        msg.encodeAsByteBuf(out);
    }
}
