package io.netty.resolver.dns;

import java.net.InetSocketAddress;

final class SingletonDnsServerAddresses extends DnsServerAddresses {

    private final InetSocketAddress address;

    private final DnsServerAddressStream stream = new DnsServerAddressStream() {
        @Override
        public InetSocketAddress next() {
            return address;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public DnsServerAddressStream duplicate() {
            return this;
        }

        @Override
        public String toString() {
            return SingletonDnsServerAddresses.this.toString();
        }
    };

    SingletonDnsServerAddresses(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public DnsServerAddressStream stream() {
        return stream;
    }

    @Override
    public String toString() {
        return "singleton(" + address + ")";
    }
}
