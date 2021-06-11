package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelPipeline;

/**
 * Marker interface which all WebSocketFrame decoders need to implement.
 * <p>
 * This makes it easier to access the added encoder later in the {@link ChannelPipeline}.
 */
public interface WebSocketFrameDecoder extends ChannelInboundHandler {
}
