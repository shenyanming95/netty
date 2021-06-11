package io.netty.handler.codec.rtsp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectEncoder;

/**
 * Encodes an RTSP message represented in {@link FullHttpMessage} into
 * a {@link ByteBuf}.
 *
 * @deprecated Use {@link RtspEncoder} instead.
 */
@Sharable
@Deprecated
public abstract class RtspObjectEncoder<H extends HttpMessage> extends HttpObjectEncoder<H> {

    /**
     * Creates a new instance.
     */
    protected RtspObjectEncoder() {
    }

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return msg instanceof FullHttpMessage;
    }
}
