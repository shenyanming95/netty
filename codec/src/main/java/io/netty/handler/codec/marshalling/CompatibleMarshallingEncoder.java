package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.marshalling.Marshaller;

/**
 * {@link MessageToByteEncoder} implementation which uses JBoss Marshalling to marshal
 * an Object.
 * <p>
 * See <a href="http://www.jboss.org/jbossmarshalling">JBoss Marshalling website</a>
 * for more information
 * <p>
 * Use {@link MarshallingEncoder} if possible.
 */
@Sharable
public class CompatibleMarshallingEncoder extends MessageToByteEncoder<Object> {

    private final MarshallerProvider provider;

    /**
     * Create a new instance of the {@link CompatibleMarshallingEncoder}
     *
     * @param provider the {@link MarshallerProvider} to use to get the {@link Marshaller} for a {@link Channel}
     */
    public CompatibleMarshallingEncoder(MarshallerProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Marshaller marshaller = provider.getMarshaller(ctx);
        marshaller.start(new ChannelBufferByteOutput(out));
        marshaller.writeObject(msg);
        marshaller.finish();
        marshaller.close();
    }

}
