package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * Combines two {@link DnsQueryLifecycleObserverFactory} into a single {@link DnsQueryLifecycleObserverFactory}.
 */
public final class BiDnsQueryLifecycleObserverFactory implements DnsQueryLifecycleObserverFactory {
    private final DnsQueryLifecycleObserverFactory a;
    private final DnsQueryLifecycleObserverFactory b;

    /**
     * Create a new instance.
     *
     * @param a The {@link DnsQueryLifecycleObserverFactory} that will receive events first.
     * @param b The {@link DnsQueryLifecycleObserverFactory} that will receive events second.
     */
    public BiDnsQueryLifecycleObserverFactory(DnsQueryLifecycleObserverFactory a, DnsQueryLifecycleObserverFactory b) {
        this.a = checkNotNull(a, "a");
        this.b = checkNotNull(b, "b");
    }

    @Override
    public DnsQueryLifecycleObserver newDnsQueryLifecycleObserver(DnsQuestion question) {
        return new BiDnsQueryLifecycleObserver(a.newDnsQueryLifecycleObserver(question), b.newDnsQueryLifecycleObserver(question));
    }
}
