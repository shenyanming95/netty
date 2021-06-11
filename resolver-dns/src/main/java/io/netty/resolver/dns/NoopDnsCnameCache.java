package io.netty.resolver.dns;

import io.netty.channel.EventLoop;

public final class NoopDnsCnameCache implements DnsCnameCache {

    public static final NoopDnsCnameCache INSTANCE = new NoopDnsCnameCache();

    private NoopDnsCnameCache() {
    }

    @Override
    public String get(String hostname) {
        return null;
    }

    @Override
    public void cache(String hostname, String cname, long originalTtl, EventLoop loop) {
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
