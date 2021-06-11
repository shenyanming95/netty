package io.netty.handler.codec.dns;

import io.netty.util.internal.UnstableApi;

/**
 * A DNS question.
 */
@UnstableApi
public interface DnsQuestion extends DnsRecord {
    /**
     * An unused property. This method will always return {@code 0}.
     */
    @Override
    long timeToLive();
}
