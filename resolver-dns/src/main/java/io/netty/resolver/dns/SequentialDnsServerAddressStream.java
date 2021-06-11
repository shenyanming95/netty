package io.netty.resolver.dns;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;

final class SequentialDnsServerAddressStream implements DnsServerAddressStream {

    private final List<? extends InetSocketAddress> addresses;
    private int i;

    SequentialDnsServerAddressStream(List<? extends InetSocketAddress> addresses, int startIdx) {
        this.addresses = addresses;
        i = startIdx;
    }

    static String toString(String type, int index, Collection<? extends InetSocketAddress> addresses) {
        final StringBuilder buf = new StringBuilder(type.length() + 2 + addresses.size() * 16);
        buf.append(type).append("(index: ").append(index);
        buf.append(", addrs: (");
        for (InetSocketAddress a : addresses) {
            buf.append(a).append(", ");
        }

        buf.setLength(buf.length() - 2);
        buf.append("))");

        return buf.toString();
    }

    @Override
    public InetSocketAddress next() {
        int i = this.i;
        InetSocketAddress next = addresses.get(i);
        if (++i < addresses.size()) {
            this.i = i;
        } else {
            this.i = 0;
        }
        return next;
    }

    @Override
    public int size() {
        return addresses.size();
    }

    @Override
    public SequentialDnsServerAddressStream duplicate() {
        return new SequentialDnsServerAddressStream(addresses, i);
    }

    @Override
    public String toString() {
        return toString("sequential", i, addresses);
    }
}
