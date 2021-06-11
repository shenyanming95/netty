package io.netty.resolver.dns;

/**
 * Provides an opportunity to override which {@link DnsServerAddressStream} is used to resolve a specific hostname.
 * <p>
 * For example this can be used to represent <a href="https://linux.die.net/man/5/resolver">/etc/resolv.conf</a> and
 * <a href="https://developer.apple.com/legacy/library/documentation/Darwin/Reference/ManPages/man5/resolver.5.html">
 * /etc/resolver</a>.
 */
public interface DnsServerAddressStreamProvider {
    /**
     * Ask this provider for the name servers to query for {@code hostname}.
     *
     * @param hostname The hostname for which to lookup the DNS server addressed to use.
     *                 If this is the final {@link DnsServerAddressStreamProvider} to be queried then generally empty
     *                 string or {@code '.'} correspond to the default {@link DnsServerAddressStream}.
     * @return The {@link DnsServerAddressStream} which should be used to resolve {@code hostname}.
     */
    DnsServerAddressStream nameServerAddressStream(String hostname);
}
