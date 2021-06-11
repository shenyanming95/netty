package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;

/**
 * Used to generate new instances of {@link DnsQueryLifecycleObserver}.
 */
public interface DnsQueryLifecycleObserverFactory {
    /**
     * Create a new instance of a {@link DnsQueryLifecycleObserver}. This will be called at the start of a new query.
     *
     * @param question The question being asked.
     * @return a new instance of a {@link DnsQueryLifecycleObserver}.
     */
    DnsQueryLifecycleObserver newDnsQueryLifecycleObserver(DnsQuestion question);
}
