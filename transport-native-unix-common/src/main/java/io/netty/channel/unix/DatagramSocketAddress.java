package io.netty.channel.unix;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Act as special {@link InetSocketAddress} to be able to easily pass all needed data from JNI without the need
 * to create more objects then needed.
 * <p>
 * <strong>Internal usage only!</strong>
 */
public final class DatagramSocketAddress extends InetSocketAddress {
    private static final long serialVersionUID = 3094819287843178401L;

    // holds the amount of received bytes
    private final int receivedAmount;
    private final DatagramSocketAddress localAddress;

    DatagramSocketAddress(byte[] addr, int scopeId, int port, int receivedAmount, DatagramSocketAddress local) throws UnknownHostException {
        super(newAddress(addr, scopeId), port);
        this.receivedAmount = receivedAmount;
        localAddress = local;
    }

    private static InetAddress newAddress(byte[] bytes, int scopeId) throws UnknownHostException {
        if (bytes.length == 4) {
            return InetAddress.getByAddress(bytes);
        }
        return Inet6Address.getByAddress(null, bytes, scopeId);
    }

    public DatagramSocketAddress localAddress() {
        return localAddress;
    }

    public int receivedAmount() {
        return receivedAmount;
    }
}
