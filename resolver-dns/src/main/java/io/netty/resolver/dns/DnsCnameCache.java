package io.netty.resolver.dns;

import io.netty.channel.EventLoop;

/**
 * A cache for {@code CNAME}s.
 */
public interface DnsCnameCache {

    /**
     * Returns the cached cname for the given hostname.
     *
     * @param hostname the hostname
     * @return the cached entries or an {@code null} if none.
     */
    String get(String hostname);

    /**
     * Caches a cname entry that should be used for the given hostname.
     *
     * @param hostname    the hostname
     * @param cname       the cname mapping.
     * @param originalTtl the TTL as returned by the DNS server
     * @param loop        the {@link EventLoop} used to register the TTL timeout
     */
    void cache(String hostname, String cname, long originalTtl, EventLoop loop);

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
