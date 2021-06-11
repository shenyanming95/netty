package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.UnstableApi;

/**
 * Decodes a DNS record into its object representation.
 *
 * @see DatagramDnsResponseDecoder
 */
@UnstableApi
public interface DnsRecordDecoder {

    DnsRecordDecoder DEFAULT = new DefaultDnsRecordDecoder();

    /**
     * Decodes a DNS question into its object representation.
     *
     * @param in the input buffer which contains a DNS question at its reader index
     */
    DnsQuestion decodeQuestion(ByteBuf in) throws Exception;

    /**
     * Decodes a DNS record into its object representation.
     *
     * @param in the input buffer which contains a DNS record at its reader index
     * @return the decoded record, or {@code null} if there are not enough data in the input buffer
     */
    <T extends DnsRecord> T decodeRecord(ByteBuf in) throws Exception;
}
