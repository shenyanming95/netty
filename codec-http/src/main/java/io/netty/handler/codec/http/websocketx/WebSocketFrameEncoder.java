package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPipeline;

/**
 * Marker interface which all WebSocketFrame encoders need to implement.
 * <p>
 * This makes it easier to access the added encoder later in the {@link ChannelPipeline}.
 */
public interface WebSocketFrameEncoder extends ChannelOutboundHandler {
}
