package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/**
 * Default implementation of {@link UnmarshallerProvider} which will just create a new {@link Unmarshaller}
 * on every call to {@link #getUnmarshaller(ChannelHandlerContext)}
 */
public class DefaultUnmarshallerProvider implements UnmarshallerProvider {

    private final MarshallerFactory factory;
    private final MarshallingConfiguration config;

    /**
     * Create a new instance of {@link DefaultMarshallerProvider}
     *
     * @param factory the {@link MarshallerFactory} to use to create {@link Unmarshaller}
     * @param config  the {@link MarshallingConfiguration}
     */
    public DefaultUnmarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
        this.factory = factory;
        this.config = config;
    }

    @Override
    public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception {
        return factory.createUnmarshaller(config);
    }

}
