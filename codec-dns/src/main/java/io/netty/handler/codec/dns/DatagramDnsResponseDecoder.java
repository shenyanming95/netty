package io.netty.handler.codec.dns;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.UnstableApi;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Decodes a {@link DatagramPacket} into a {@link DatagramDnsResponse}.
 */
@UnstableApi
@ChannelHandler.Sharable
public class DatagramDnsResponseDecoder extends MessageToMessageDecoder<DatagramPacket> {

    private final DnsResponseDecoder<InetSocketAddress> responseDecoder;

    /**
     * Creates a new decoder with {@linkplain DnsRecordDecoder#DEFAULT the default record decoder}.
     */
    public DatagramDnsResponseDecoder() {
        this(DnsRecordDecoder.DEFAULT);
    }

    /**
     * Creates a new decoder with the specified {@code recordDecoder}.
     */
    public DatagramDnsResponseDecoder(DnsRecordDecoder recordDecoder) {
        this.responseDecoder = new DnsResponseDecoder<InetSocketAddress>(recordDecoder) {
            @Override
            protected DnsResponse newResponse(InetSocketAddress sender, InetSocketAddress recipient, int id, DnsOpCode opCode, DnsResponseCode responseCode) {
                return new DatagramDnsResponse(sender, recipient, id, opCode, responseCode);
            }
        };
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        out.add(decodeResponse(ctx, packet));
    }

    protected DnsResponse decodeResponse(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        return responseDecoder.decode(packet.sender(), packet.recipient(), packet.content());
    }
}
