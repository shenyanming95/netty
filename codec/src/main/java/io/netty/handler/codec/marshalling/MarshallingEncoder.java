package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.marshalling.Marshaller;

/**
 * {@link MessageToByteEncoder} implementation which uses JBoss Marshalling to marshal
 * an Object. Be aware that this encoder is not compatible with an other client that just use
 * JBoss Marshalling as it includes the size of every {@link Object} that gets serialized in
 * front of the {@link Object} itself.
 * <p>
 * Use this with {@link MarshallingDecoder}
 * <p>
 * See <a href="http://www.jboss.org/jbossmarshalling">JBoss Marshalling website</a>
 * for more information
 */
@Sharable
public class MarshallingEncoder extends MessageToByteEncoder<Object> {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    private final MarshallerProvider provider;

    /**
     * Creates a new encoder.
     *
     * @param provider the {@link MarshallerProvider} to use
     */
    public MarshallingEncoder(MarshallerProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Marshaller marshaller = provider.getMarshaller(ctx);
        int lengthPos = out.writerIndex();
        out.writeBytes(LENGTH_PLACEHOLDER);
        ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
        marshaller.start(output);
        marshaller.writeObject(msg);
        marshaller.finish();
        marshaller.close();

        out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
    }
}
