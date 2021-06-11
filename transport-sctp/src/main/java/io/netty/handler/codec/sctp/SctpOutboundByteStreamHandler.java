package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * A ChannelHandler which transform {@link ByteBuf} to {@link SctpMessage}  and send it through a specific stream
 * with given protocol identifier.
 * Unordered delivery of all messages may be requested by passing unordered = true to the constructor.
 */
public class SctpOutboundByteStreamHandler extends MessageToMessageEncoder<ByteBuf> {
    private final int streamIdentifier;
    private final int protocolIdentifier;
    private final boolean unordered;

    /**
     * @param streamIdentifier   stream number, this should be >=0 or <= max stream number of the association.
     * @param protocolIdentifier supported application protocol id.
     */
    public SctpOutboundByteStreamHandler(int streamIdentifier, int protocolIdentifier) {
        this(streamIdentifier, protocolIdentifier, false);
    }

    /**
     * @param streamIdentifier   stream number, this should be >=0 or <= max stream number of the association.
     * @param protocolIdentifier supported application protocol id.
     * @param unordered          if {@literal true}, SCTP Data Chunks will be sent with the U (unordered) flag set.
     */
    public SctpOutboundByteStreamHandler(int streamIdentifier, int protocolIdentifier, boolean unordered) {
        this.streamIdentifier = streamIdentifier;
        this.protocolIdentifier = protocolIdentifier;
        this.unordered = unordered;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(new SctpMessage(protocolIdentifier, streamIdentifier, unordered, msg.retain()));
    }
}
