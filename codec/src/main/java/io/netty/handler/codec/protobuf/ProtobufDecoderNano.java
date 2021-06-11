package io.netty.handler.codec.protobuf;

import com.google.protobuf.nano.MessageNano;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.ObjectUtil;

import java.util.List;

/**
 * Decodes a received {@link ByteBuf} into a
 * <a href="https://github.com/google/protobuf">Google Protocol Buffers</a>
 * {@link MessageNano}. Please note that this decoder must
 * be used with a proper {@link ByteToMessageDecoder} such as {@link LengthFieldBasedFrameDecoder}
 * if you are using a stream-based transport such as TCP/IP. A typical setup for TCP/IP would be:
 * <pre>
 * {@link ChannelPipeline} pipeline = ...;
 *
 * // Decoders
 * pipeline.addLast("frameDecoder",
 *                  new {@link LengthFieldBasedFrameDecoder}(1048576, 0, 4, 0, 4));
 * pipeline.addLast("protobufDecoder",
 *                  new {@link ProtobufDecoderNano}(MyMessage.getDefaultInstance()));
 *
 * // Encoder
 * pipeline.addLast("frameEncoder", new {@link io.netty.handler.codec.LengthFieldPrepender}(4));
 * pipeline.addLast("protobufEncoder", new {@link ProtobufEncoderNano}());
 * </pre>
 * and then you can use a {@code MyMessage} instead of a {@link ByteBuf}
 * as a message:
 * <pre>
 * void channelRead({@link ChannelHandlerContext} ctx, Object msg) {
 *     MyMessage req = (MyMessage) msg;
 *     MyMessage res = MyMessage.newBuilder().setText(
 *                               "Did you say '" + req.getText() + "'?").build();
 *     ch.write(res);
 * }
 * </pre>
 */
@Sharable
public class ProtobufDecoderNano extends MessageToMessageDecoder<ByteBuf> {
    private final Class<? extends MessageNano> clazz;

    /**
     * Creates a new instance.
     */
    public ProtobufDecoderNano(Class<? extends MessageNano> clazz) {
        this.clazz = ObjectUtil.checkNotNull(clazz, "You must provide a Class");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final byte[] array;
        final int offset;
        final int length = msg.readableBytes();
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
            offset = 0;
        }
        MessageNano prototype = clazz.getConstructor().newInstance();
        out.add(MessageNano.mergeFrom(prototype, array, offset, length));
    }
}
