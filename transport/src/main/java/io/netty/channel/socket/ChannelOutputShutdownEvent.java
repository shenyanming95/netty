package io.netty.channel.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.internal.UnstableApi;

/**
 * Special event which will be fired and passed to the
 * {@link ChannelInboundHandler#userEventTriggered(ChannelHandlerContext, Object)} methods once the output of
 * a {@link SocketChannel} was shutdown.
 */
@UnstableApi
public final class ChannelOutputShutdownEvent {
    public static final ChannelOutputShutdownEvent INSTANCE = new ChannelOutputShutdownEvent();

    private ChannelOutputShutdownEvent() {
    }
}
