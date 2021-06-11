package io.netty.resolver.dns;

import java.util.List;

/**
 * A {@link DnsServerAddressStreamProvider} which iterates through a collection of
 * {@link DnsServerAddressStreamProvider} until the first non-{@code null} result is found.
 */
public final class MultiDnsServerAddressStreamProvider implements DnsServerAddressStreamProvider {
    private final DnsServerAddressStreamProvider[] providers;

    /**
     * Create a new instance.
     *
     * @param providers The providers to use for DNS resolution. They will be queried in order.
     */
    public MultiDnsServerAddressStreamProvider(List<DnsServerAddressStreamProvider> providers) {
        this.providers = providers.toArray(new DnsServerAddressStreamProvider[0]);
    }

    /**
     * Create a new instance.
     *
     * @param providers The providers to use for DNS resolution. They will be queried in order.
     */
    public MultiDnsServerAddressStreamProvider(DnsServerAddressStreamProvider... providers) {
        this.providers = providers.clone();
    }

    @Override
    public DnsServerAddressStream nameServerAddressStream(String hostname) {
        for (DnsServerAddressStreamProvider provider : providers) {
            DnsServerAddressStream stream = provider.nameServerAddressStream(hostname);
            if (stream != null) {
                return stream;
            }
        }
        return null;
    }
}
