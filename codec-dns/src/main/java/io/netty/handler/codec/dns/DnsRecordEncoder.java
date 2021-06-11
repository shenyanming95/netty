package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.UnstableApi;

/**
 * Encodes a {@link DnsRecord} into binary representation.
 *
 * @see DatagramDnsQueryEncoder
 */
@UnstableApi
public interface DnsRecordEncoder {

    DnsRecordEncoder DEFAULT = new DefaultDnsRecordEncoder();

    /**
     * Encodes a {@link DnsQuestion}.
     *
     * @param out the output buffer where the encoded question will be written to
     */
    void encodeQuestion(DnsQuestion question, ByteBuf out) throws Exception;

    /**
     * Encodes a {@link DnsRecord}.
     *
     * @param out the output buffer where the encoded record will be written to
     */
    void encodeRecord(DnsRecord record, ByteBuf out) throws Exception;
}
