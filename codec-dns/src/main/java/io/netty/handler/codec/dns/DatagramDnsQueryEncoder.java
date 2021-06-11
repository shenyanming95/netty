package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.UnstableApi;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Encodes a {@link DatagramDnsQuery} (or an {@link AddressedEnvelope} of {@link DnsQuery}} into a
 * {@link DatagramPacket}.
 */
@UnstableApi
@ChannelHandler.Sharable
public class DatagramDnsQueryEncoder extends MessageToMessageEncoder<AddressedEnvelope<DnsQuery, InetSocketAddress>> {

    private final DnsQueryEncoder encoder;

    /**
     * Creates a new encoder with {@linkplain DnsRecordEncoder#DEFAULT the default record encoder}.
     */
    public DatagramDnsQueryEncoder() {
        this(DnsRecordEncoder.DEFAULT);
    }

    /**
     * Creates a new encoder with the specified {@code recordEncoder}.
     */
    public DatagramDnsQueryEncoder(DnsRecordEncoder recordEncoder) {
        this.encoder = new DnsQueryEncoder(recordEncoder);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AddressedEnvelope<DnsQuery, InetSocketAddress> in, List<Object> out) throws Exception {

        final InetSocketAddress recipient = in.recipient();
        final DnsQuery query = in.content();
        final ByteBuf buf = allocateBuffer(ctx, in);

        boolean success = false;
        try {
            encoder.encode(query, buf);
            success = true;
        } finally {
            if (!success) {
                buf.release();
            }
        }

        out.add(new DatagramPacket(buf, recipient, null));
    }

    /**
     * Allocate a {@link ByteBuf} which will be used for constructing a datagram packet.
     * Sub-classes may override this method to return a {@link ByteBuf} with a perfect matching initial capacity.
     */
    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, @SuppressWarnings("unused") AddressedEnvelope<DnsQuery, InetSocketAddress> msg) throws Exception {
        return ctx.alloc().ioBuffer(1024);
    }
}
