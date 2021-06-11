package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map.Entry;

/**
 * Encodes a {@link StompFrame} or a {@link StompSubframe} into a {@link ByteBuf}.
 */
public class StompSubframeEncoder extends MessageToMessageEncoder<StompSubframe> {

    private static ByteBuf encodeContent(StompContentSubframe content, ChannelHandlerContext ctx) {
        if (content instanceof LastStompContentSubframe) {
            ByteBuf buf = ctx.alloc().buffer(content.content().readableBytes() + 1);
            buf.writeBytes(content.content());
            buf.writeByte(StompConstants.NUL);
            return buf;
        } else {
            return content.content().retain();
        }
    }

    private static ByteBuf encodeFrame(StompHeadersSubframe frame, ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();

        buf.writeCharSequence(frame.command().toString(), CharsetUtil.UTF_8);
        buf.writeByte(StompConstants.LF);
        for (Entry<CharSequence, CharSequence> entry : frame.headers()) {
            ByteBufUtil.writeUtf8(buf, entry.getKey());
            buf.writeByte(StompConstants.COLON);
            ByteBufUtil.writeUtf8(buf, entry.getValue());
            buf.writeByte(StompConstants.LF);
        }
        buf.writeByte(StompConstants.LF);
        return buf;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, StompSubframe msg, List<Object> out) throws Exception {
        if (msg instanceof StompFrame) {
            StompFrame frame = (StompFrame) msg;
            ByteBuf frameBuf = encodeFrame(frame, ctx);
            if (frame.content().isReadable()) {
                out.add(frameBuf);
                ByteBuf contentBuf = encodeContent(frame, ctx);
                out.add(contentBuf);
            } else {
                frameBuf.writeByte(StompConstants.NUL);
                out.add(frameBuf);
            }
        } else if (msg instanceof StompHeadersSubframe) {
            StompHeadersSubframe frame = (StompHeadersSubframe) msg;
            ByteBuf buf = encodeFrame(frame, ctx);
            out.add(buf);
        } else if (msg instanceof StompContentSubframe) {
            StompContentSubframe stompContentSubframe = (StompContentSubframe) msg;
            ByteBuf buf = encodeContent(stompContentSubframe, ctx);
            out.add(buf);
        }
    }
}
