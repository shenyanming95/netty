package io.netty.resolver.dns;

import io.netty.channel.EventLoop;

import java.net.InetSocketAddress;

/**
 * Cache which stores the nameservers that should be used to resolve a specific hostname.
 */
public interface AuthoritativeDnsServerCache {

    /**
     * Returns the cached nameservers that should be used to resolve the given hostname. The returned
     * {@link DnsServerAddressStream} may contain unresolved {@link InetSocketAddress}es that will be resolved
     * when needed while resolving other domain names.
     *
     * @param hostname the hostname
     * @return the cached entries or an {@code null} if none.
     */
    DnsServerAddressStream get(String hostname);

    /**
     * Caches a nameserver that should be used to resolve the given hostname.
     *
     * @param hostname    the hostname
     * @param address     the nameserver address (which may be unresolved).
     * @param originalTtl the TTL as returned by the DNS server
     * @param loop        the {@link EventLoop} used to register the TTL timeout
     */
    void cache(String hostname, InetSocketAddress address, long originalTtl, EventLoop loop);

    /**
     * Clears all cached nameservers.
     *
     * @see #clear(String)
     */
    void clear();

    /**
     * Clears the cached nameservers for the specified hostname.
     *
     * @return {@code true} if and only if there was an entry for the specified host name in the cache and
     * it has been removed by this method
     */
    boolean clear(String hostname);
}
