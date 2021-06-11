package io.netty.resolver.dns;

import io.netty.channel.EventLoop;

import java.net.InetSocketAddress;

/**
 * A noop {@link AuthoritativeDnsServerCache} that actually never caches anything.
 */
public final class NoopAuthoritativeDnsServerCache implements AuthoritativeDnsServerCache {
    public static final NoopAuthoritativeDnsServerCache INSTANCE = new NoopAuthoritativeDnsServerCache();

    private NoopAuthoritativeDnsServerCache() {
    }

    @Override
    public DnsServerAddressStream get(String hostname) {
        return null;
    }

    @Override
    public void cache(String hostname, InetSocketAddress address, long originalTtl, EventLoop loop) {
        // NOOP
    }

    @Override
    public void clear() {
        // NOOP
    }

    @Override
    public boolean clear(String hostname) {
        return false;
    }
}
