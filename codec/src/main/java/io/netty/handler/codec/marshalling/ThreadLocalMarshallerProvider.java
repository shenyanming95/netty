package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * {@link UnmarshallerProvider} implementation which use a {@link ThreadLocal} to store references
 * to {@link Marshaller} instances. This may give you some performance boost if you need to marshall
 * many small {@link Object}'s and your actual Thread count is not to big
 */
public class ThreadLocalMarshallerProvider implements MarshallerProvider {
    private final FastThreadLocal<Marshaller> marshallers = new FastThreadLocal<Marshaller>();

    private final MarshallerFactory factory;
    private final MarshallingConfiguration config;

    /**
     * Create a new instance of the {@link ThreadLocalMarshallerProvider}
     *
     * @param factory the {@link MarshallerFactory} to use to create {@link Marshaller}'s if needed
     * @param config  the {@link MarshallingConfiguration} to use
     */
    public ThreadLocalMarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
        this.factory = factory;
        this.config = config;
    }

    @Override
    public Marshaller getMarshaller(ChannelHandlerContext ctx) throws Exception {
        Marshaller marshaller = marshallers.get();
        if (marshaller == null) {
            marshaller = factory.createMarshaller(config);
            marshallers.set(marshaller);
        }
        return marshaller;
    }
}
