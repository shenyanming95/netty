package io.netty.handler.codec.dns;

import io.netty.util.internal.UnstableApi;

/**
 * Represents a section of a {@link DnsMessage}.
 */
@UnstableApi
public enum DnsSection {
    /**
     * The section that contains {@link DnsQuestion}s.
     */
    QUESTION,
    /**
     * The section that contains the answer {@link DnsRecord}s.
     */
    ANSWER,
    /**
     * The section that contains the authority {@link DnsRecord}s.
     */
    AUTHORITY,
    /**
     * The section that contains the additional {@link DnsRecord}s.
     */
    ADDITIONAL
}
