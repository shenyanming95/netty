package io.netty.resolver.dns;

import java.net.InetAddress;

/**
 * Represents the results from a previous DNS query which can be cached.
 */
public interface DnsCacheEntry {
    /**
     * Get the resolved address.
     * <p>
     * This may be null if the resolution failed, and in that case {@link #cause()} will describe the failure.
     *
     * @return the resolved address.
     */
    InetAddress address();

    /**
     * If the DNS query failed this will provide the rational.
     *
     * @return the rational for why the DNS query failed, or {@code null} if the query hasn't failed.
     */
    Throwable cause();
}
