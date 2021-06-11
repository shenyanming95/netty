package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socks.SocksInitResponseDecoder.State;

import java.util.List;

/**
 * Decodes {@link ByteBuf}s into {@link SocksInitResponse}.
 * Before returning SocksResponse decoder removes itself from pipeline.
 */
public class SocksInitResponseDecoder extends ReplayingDecoder<State> {

    public SocksInitResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch (state()) {
            case CHECK_PROTOCOL_VERSION: {
                if (byteBuf.readByte() != SocksProtocolVersion.SOCKS5.byteValue()) {
                    out.add(SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE);
                    break;
                }
                checkpoint(State.READ_PREFERRED_AUTH_TYPE);
            }
            case READ_PREFERRED_AUTH_TYPE: {
                SocksAuthScheme authScheme = SocksAuthScheme.valueOf(byteBuf.readByte());
                out.add(new SocksInitResponse(authScheme));
                break;
            }
            default: {
                throw new Error();
            }
        }
        ctx.pipeline().remove(this);
    }

    enum State {
        CHECK_PROTOCOL_VERSION, READ_PREFERRED_AUTH_TYPE
    }
}
