package io.netty.channel.socket;

import io.netty.util.NetUtil;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

/**
 * Internet Protocol (IP) families used byte the {@link DatagramChannel}
 */
public enum InternetProtocolFamily {
    IPv4(Inet4Address.class, 1, NetUtil.LOCALHOST4), IPv6(Inet6Address.class, 2, NetUtil.LOCALHOST6);

    private final Class<? extends InetAddress> addressType;
    private final int addressNumber;
    private final InetAddress localHost;

    InternetProtocolFamily(Class<? extends InetAddress> addressType, int addressNumber, InetAddress localHost) {
        this.addressType = addressType;
        this.addressNumber = addressNumber;
        this.localHost = localHost;
    }

    /**
     * Returns the {@link InternetProtocolFamily} for the given {@link InetAddress}.
     */
    public static InternetProtocolFamily of(InetAddress address) {
        if (address instanceof Inet4Address) {
            return IPv4;
        }
        if (address instanceof Inet6Address) {
            return IPv6;
        }
        throw new IllegalArgumentException("address " + address + " not supported");
    }

    /**
     * Returns the address type of this protocol family.
     */
    public Class<? extends InetAddress> addressType() {
        return addressType;
    }

    /**
     * Returns the
     * <a href="http://www.iana.org/assignments/address-family-numbers/address-family-numbers.xhtml">address number</a>
     * of the family.
     */
    public int addressNumber() {
        return addressNumber;
    }

    /**
     * Returns the {@link InetAddress} that represent the {@code LOCALHOST} for the family.
     */
    public InetAddress localhost() {
        return localHost;
    }
}
