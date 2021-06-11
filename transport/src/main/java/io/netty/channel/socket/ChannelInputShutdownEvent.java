package io.netty.channel.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

/**
 * Special event which will be fired and passed to the
 * {@link ChannelInboundHandler#userEventTriggered(ChannelHandlerContext, Object)} methods once the input of
 * a {@link SocketChannel} was shutdown and the {@link SocketChannelConfig#isAllowHalfClosure()} method returns
 * {@code true}.
 */
public final class ChannelInputShutdownEvent {

    /**
     * Instance to use
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final ChannelInputShutdownEvent INSTANCE = new ChannelInputShutdownEvent();

    private ChannelInputShutdownEvent() {
    }
}
