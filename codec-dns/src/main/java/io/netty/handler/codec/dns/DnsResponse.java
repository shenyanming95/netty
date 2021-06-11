package io.netty.handler.codec.dns;

import io.netty.util.internal.UnstableApi;

/**
 * A DNS response message.
 */
@UnstableApi
public interface DnsResponse extends DnsMessage {

    /**
     * Returns {@code true} if responding server is authoritative for the domain
     * name in the query message.
     */
    boolean isAuthoritativeAnswer();

    /**
     * Set to {@code true} if responding server is authoritative for the domain
     * name in the query message.
     *
     * @param authoritativeAnswer flag for authoritative answer
     */
    DnsResponse setAuthoritativeAnswer(boolean authoritativeAnswer);

    /**
     * Returns {@code true} if response has been truncated, usually if it is
     * over 512 bytes.
     */
    boolean isTruncated();

    /**
     * Set to {@code true} if response has been truncated (usually happens for
     * responses over 512 bytes).
     *
     * @param truncated flag for truncation
     */
    DnsResponse setTruncated(boolean truncated);

    /**
     * Returns {@code true} if DNS server can handle recursive queries.
     */
    boolean isRecursionAvailable();

    /**
     * Set to {@code true} if DNS server can handle recursive queries.
     *
     * @param recursionAvailable flag for recursion availability
     */
    DnsResponse setRecursionAvailable(boolean recursionAvailable);

    /**
     * Returns the 4 bit return code.
     */
    DnsResponseCode code();

    /**
     * Sets the response code for this message.
     *
     * @param code the response code
     */
    DnsResponse setCode(DnsResponseCode code);

    @Override
    DnsResponse setId(int id);

    @Override
    DnsResponse setOpCode(DnsOpCode opCode);

    @Override
    DnsResponse setRecursionDesired(boolean recursionDesired);

    @Override
    DnsResponse setZ(int z);

    @Override
    DnsResponse setRecord(DnsSection section, DnsRecord record);

    @Override
    DnsResponse addRecord(DnsSection section, DnsRecord record);

    @Override
    DnsResponse addRecord(DnsSection section, int index, DnsRecord record);

    @Override
    DnsResponse clear(DnsSection section);

    @Override
    DnsResponse clear();

    @Override
    DnsResponse touch();

    @Override
    DnsResponse touch(Object hint);

    @Override
    DnsResponse retain();

    @Override
    DnsResponse retain(int increment);
}
