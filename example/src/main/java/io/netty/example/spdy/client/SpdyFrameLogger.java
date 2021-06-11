package io.netty.example.spdy.client;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.spdy.SpdyFrame;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Logs SPDY frames for debugging purposes.
 */
public class SpdyFrameLogger extends ChannelDuplexHandler {

    protected final InternalLogger logger;
    private final InternalLogLevel level;

    public SpdyFrameLogger(InternalLogLevel level) {
        this.level = ObjectUtil.checkNotNull(level, "level");
        this.logger = InternalLoggerFactory.getInstance(getClass());
    }

    private static boolean acceptMessage(Object msg) {
        return msg instanceof SpdyFrame;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (acceptMessage(msg)) {
            log((SpdyFrame) msg, Direction.INBOUND);
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if (acceptMessage(msg)) {
            log((SpdyFrame) msg, Direction.OUTBOUND);
        }
        ctx.write(msg, promise);
    }

    private void log(SpdyFrame msg, Direction d) {
        if (logger.isEnabled(level)) {
            StringBuilder b = new StringBuilder(200).append("\n----------------").append(d.name()).append("--------------------\n").append(msg).append("\n------------------------------------");

            logger.log(level, b.toString());
        }
    }

    private enum Direction {
        INBOUND, OUTBOUND
    }
}
