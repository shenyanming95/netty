package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.UnstableApi;

import java.util.List;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * Decodes a {@link DatagramPacket} into a {@link DatagramDnsQuery}.
 */
@UnstableApi
@ChannelHandler.Sharable
public class DatagramDnsQueryDecoder extends MessageToMessageDecoder<DatagramPacket> {

    private final DnsRecordDecoder recordDecoder;

    /**
     * Creates a new decoder with {@linkplain DnsRecordDecoder#DEFAULT the default record decoder}.
     */
    public DatagramDnsQueryDecoder() {
        this(DnsRecordDecoder.DEFAULT);
    }

    /**
     * Creates a new decoder with the specified {@code recordDecoder}.
     */
    public DatagramDnsQueryDecoder(DnsRecordDecoder recordDecoder) {
        this.recordDecoder = checkNotNull(recordDecoder, "recordDecoder");
    }

    private static DnsQuery newQuery(DatagramPacket packet, ByteBuf buf) {
        final int id = buf.readUnsignedShort();

        final int flags = buf.readUnsignedShort();
        if (flags >> 15 == 1) {
            throw new CorruptedFrameException("not a query");
        }
        final DnsQuery query = new DatagramDnsQuery(packet.sender(), packet.recipient(), id, DnsOpCode.valueOf((byte) (flags >> 11 & 0xf)));
        query.setRecursionDesired((flags >> 8 & 1) == 1);
        query.setZ(flags >> 4 & 0x7);
        return query;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        final ByteBuf buf = packet.content();

        final DnsQuery query = newQuery(packet, buf);
        boolean success = false;
        try {
            final int questionCount = buf.readUnsignedShort();
            final int answerCount = buf.readUnsignedShort();
            final int authorityRecordCount = buf.readUnsignedShort();
            final int additionalRecordCount = buf.readUnsignedShort();

            decodeQuestions(query, buf, questionCount);
            decodeRecords(query, DnsSection.ANSWER, buf, answerCount);
            decodeRecords(query, DnsSection.AUTHORITY, buf, authorityRecordCount);
            decodeRecords(query, DnsSection.ADDITIONAL, buf, additionalRecordCount);

            out.add(query);
            success = true;
        } finally {
            if (!success) {
                query.release();
            }
        }
    }

    private void decodeQuestions(DnsQuery query, ByteBuf buf, int questionCount) throws Exception {
        for (int i = questionCount; i > 0; i--) {
            query.addRecord(DnsSection.QUESTION, recordDecoder.decodeQuestion(buf));
        }
    }

    private void decodeRecords(DnsQuery query, DnsSection section, ByteBuf buf, int count) throws Exception {
        for (int i = count; i > 0; i--) {
            final DnsRecord r = recordDecoder.decodeRecord(buf);
            if (r == null) {
                // Truncated response
                break;
            }

            query.addRecord(section, r);
        }
    }
}
