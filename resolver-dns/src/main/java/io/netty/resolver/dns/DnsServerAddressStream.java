package io.netty.resolver.dns;

import java.net.InetSocketAddress;

/**
 * An infinite stream of DNS server addresses.
 */
public interface DnsServerAddressStream {
    /**
     * Retrieves the next DNS server address from the stream.
     */
    InetSocketAddress next();

    /**
     * Get the number of times {@link #next()} will return a distinct element before repeating or terminating.
     *
     * @return the number of times {@link #next()} will return a distinct element before repeating or terminating.
     */
    int size();

    /**
     * Duplicate this object. The result of this should be able to be independently iterated over via {@link #next()}.
     * <p>
     * Note that {@link #clone()} isn't used because it may make sense for some implementations to have the following
     * relationship {@code x.duplicate() == x}.
     *
     * @return A duplicate of this object.
     */
    DnsServerAddressStream duplicate();
}
