package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.internal.UnstableApi;

/**
 * A generic {@link DnsRecord} that contains an undecoded {@code RDATA}.
 */
@UnstableApi
public interface DnsRawRecord extends DnsRecord, ByteBufHolder {
    @Override
    DnsRawRecord copy();

    @Override
    DnsRawRecord duplicate();

    @Override
    DnsRawRecord retainedDuplicate();

    @Override
    DnsRawRecord replace(ByteBuf content);

    @Override
    DnsRawRecord retain();

    @Override
    DnsRawRecord retain(int increment);

    @Override
    DnsRawRecord touch();

    @Override
    DnsRawRecord touch(Object hint);
}
