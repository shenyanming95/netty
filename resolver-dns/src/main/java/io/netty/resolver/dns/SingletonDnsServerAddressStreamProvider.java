package io.netty.resolver.dns;

import java.net.InetSocketAddress;

/**
 * A {@link DnsServerAddressStreamProvider} which always uses a single DNS server for resolution.
 */
public final class SingletonDnsServerAddressStreamProvider extends UniSequentialDnsServerAddressStreamProvider {
    /**
     * Create a new instance.
     *
     * @param address The singleton address to use for every DNS resolution.
     */
    public SingletonDnsServerAddressStreamProvider(final InetSocketAddress address) {
        super(DnsServerAddresses.singleton(address));
    }
}
