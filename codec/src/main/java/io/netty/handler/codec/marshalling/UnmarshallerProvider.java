package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Unmarshaller;

/**
 * This provider is responsible to get an {@link Unmarshaller} for a {@link ChannelHandlerContext}
 */
public interface UnmarshallerProvider {

    /**
     * Get the {@link Unmarshaller} for the given {@link ChannelHandlerContext}
     */
    Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception;
}
