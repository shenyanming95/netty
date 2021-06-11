package io.netty.resolver.dns;

import io.netty.util.internal.PlatformDependent;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

final class ShuffledDnsServerAddressStream implements DnsServerAddressStream {

    private final List<InetSocketAddress> addresses;
    private int i;

    /**
     * Create a new instance.
     *
     * @param addresses The addresses are not cloned. It is assumed the caller has cloned this array or otherwise will
     *                  not modify the contents.
     */
    ShuffledDnsServerAddressStream(List<InetSocketAddress> addresses) {
        this.addresses = addresses;

        shuffle();
    }

    private ShuffledDnsServerAddressStream(List<InetSocketAddress> addresses, int startIdx) {
        this.addresses = addresses;
        i = startIdx;
    }

    private void shuffle() {
        Collections.shuffle(addresses, PlatformDependent.threadLocalRandom());
    }

    @Override
    public InetSocketAddress next() {
        int i = this.i;
        InetSocketAddress next = addresses.get(i);
        if (++i < addresses.size()) {
            this.i = i;
        } else {
            this.i = 0;
            shuffle();
        }
        return next;
    }

    @Override
    public int size() {
        return addresses.size();
    }

    @Override
    public ShuffledDnsServerAddressStream duplicate() {
        return new ShuffledDnsServerAddressStream(addresses, i);
    }

    @Override
    public String toString() {
        return SequentialDnsServerAddressStream.toString("shuffled", i, addresses);
    }
}
