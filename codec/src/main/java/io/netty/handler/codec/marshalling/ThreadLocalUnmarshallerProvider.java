package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/**
 * {@link UnmarshallerProvider} implementation which use a {@link ThreadLocal} to store references
 * to {@link Unmarshaller} instances. This may give you some performance boost if you need to unmarshall
 * many small {@link Object}'s.
 */
public class ThreadLocalUnmarshallerProvider implements UnmarshallerProvider {
    private final FastThreadLocal<Unmarshaller> unmarshallers = new FastThreadLocal<Unmarshaller>();

    private final MarshallerFactory factory;
    private final MarshallingConfiguration config;

    /**
     * Create a new instance of the {@link ThreadLocalUnmarshallerProvider}
     *
     * @param factory the {@link MarshallerFactory} to use to create {@link Unmarshaller}'s if needed
     * @param config  the {@link MarshallingConfiguration} to use
     */
    public ThreadLocalUnmarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
        this.factory = factory;
        this.config = config;
    }

    @Override
    public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception {
        Unmarshaller unmarshaller = unmarshallers.get();
        if (unmarshaller == null) {
            unmarshaller = factory.createUnmarshaller(config);
            unmarshallers.set(unmarshaller);
        }
        return unmarshaller;
    }

}
