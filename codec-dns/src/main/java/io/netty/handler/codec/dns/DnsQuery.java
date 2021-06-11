package io.netty.handler.codec.dns;

import io.netty.util.internal.UnstableApi;

/**
 * A DNS query message.
 */
@UnstableApi
public interface DnsQuery extends DnsMessage {
    @Override
    DnsQuery setId(int id);

    @Override
    DnsQuery setOpCode(DnsOpCode opCode);

    @Override
    DnsQuery setRecursionDesired(boolean recursionDesired);

    @Override
    DnsQuery setZ(int z);

    @Override
    DnsQuery setRecord(DnsSection section, DnsRecord record);

    @Override
    DnsQuery addRecord(DnsSection section, DnsRecord record);

    @Override
    DnsQuery addRecord(DnsSection section, int index, DnsRecord record);

    @Override
    DnsQuery clear(DnsSection section);

    @Override
    DnsQuery clear();

    @Override
    DnsQuery touch();

    @Override
    DnsQuery touch(Object hint);

    @Override
    DnsQuery retain();

    @Override
    DnsQuery retain(int increment);
}
