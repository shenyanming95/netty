package io.netty.resolver.dns;

import java.net.InetSocketAddress;

import static io.netty.resolver.dns.DnsServerAddresses.sequential;

/**
 * A {@link DnsServerAddressStreamProvider} which is backed by a sequential list of DNS servers.
 */
public final class SequentialDnsServerAddressStreamProvider extends UniSequentialDnsServerAddressStreamProvider {
    /**
     * Create a new instance.
     *
     * @param addresses The addresses which will be be returned in sequential order via
     *                  {@link #nameServerAddressStream(String)}
     */
    public SequentialDnsServerAddressStreamProvider(InetSocketAddress... addresses) {
        super(sequential(addresses));
    }

    /**
     * Create a new instance.
     *
     * @param addresses The addresses which will be be returned in sequential order via
     *                  {@link #nameServerAddressStream(String)}
     */
    public SequentialDnsServerAddressStreamProvider(Iterable<? extends InetSocketAddress> addresses) {
        super(sequential(addresses));
    }
}
