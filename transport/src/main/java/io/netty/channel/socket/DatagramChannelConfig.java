package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;

/**
 * A {@link ChannelConfig} for a {@link DatagramChannel}.
 *
 * <h3>Available options</h3>
 * <p>
 * In addition to the options provided by {@link ChannelConfig},
 * {@link DatagramChannelConfig} allows the following options in the option map:
 *
 * <table border="1" cellspacing="0" cellpadding="6">
 * <tr>
 * <th>Name</th><th>Associated setter method</th>
 * </tr><tr>
 * <td>{@link ChannelOption#SO_BROADCAST}</td><td>{@link #setBroadcast(boolean)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#IP_MULTICAST_ADDR}</td><td>{@link #setInterface(InetAddress)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#IP_MULTICAST_LOOP_DISABLED}</td>
 * <td>{@link #setLoopbackModeDisabled(boolean)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#IP_MULTICAST_IF}</td>
 * <td>{@link #setNetworkInterface(NetworkInterface)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#SO_REUSEADDR}</td><td>{@link #setReuseAddress(boolean)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#SO_RCVBUF}</td><td>{@link #setReceiveBufferSize(int)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#SO_SNDBUF}</td><td>{@link #setSendBufferSize(int)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#IP_MULTICAST_TTL}</td><td>{@link #setTimeToLive(int)}</td>
 * </tr><tr>
 * <td>{@link ChannelOption#IP_TOS}</td><td>{@link #setTrafficClass(int)}</td>
 * </tr>
 * </table>
 */
public interface DatagramChannelConfig extends ChannelConfig {

    /**
     * Gets the {@link StandardSocketOptions#SO_SNDBUF} option.
     */
    int getSendBufferSize();

    /**
     * Sets the {@link StandardSocketOptions#SO_SNDBUF} option.
     */
    DatagramChannelConfig setSendBufferSize(int sendBufferSize);

    /**
     * Gets the {@link StandardSocketOptions#SO_RCVBUF} option.
     */
    int getReceiveBufferSize();

    /**
     * Sets the {@link StandardSocketOptions#SO_RCVBUF} option.
     */
    DatagramChannelConfig setReceiveBufferSize(int receiveBufferSize);

    /**
     * Gets the {@link StandardSocketOptions#IP_TOS} option.
     */
    int getTrafficClass();

    /**
     * Sets the {@link StandardSocketOptions#IP_TOS} option.
     */
    DatagramChannelConfig setTrafficClass(int trafficClass);

    /**
     * Gets the {@link StandardSocketOptions#SO_REUSEADDR} option.
     */
    boolean isReuseAddress();

    /**
     * Gets the {@link StandardSocketOptions#SO_REUSEADDR} option.
     */
    DatagramChannelConfig setReuseAddress(boolean reuseAddress);

    /**
     * Gets the {@link StandardSocketOptions#SO_BROADCAST} option.
     */
    boolean isBroadcast();

    /**
     * Sets the {@link StandardSocketOptions#SO_BROADCAST} option.
     */
    DatagramChannelConfig setBroadcast(boolean broadcast);

    /**
     * Gets the {@link StandardSocketOptions#IP_MULTICAST_LOOP} option.
     *
     * @return {@code true} if and only if the loopback mode has been disabled
     */
    boolean isLoopbackModeDisabled();

    /**
     * Sets the {@link StandardSocketOptions#IP_MULTICAST_LOOP} option.
     *
     * @param loopbackModeDisabled {@code true} if and only if the loopback mode has been disabled
     */
    DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled);

    /**
     * Gets the {@link StandardSocketOptions#IP_MULTICAST_TTL} option.
     */
    int getTimeToLive();

    /**
     * Sets the {@link StandardSocketOptions#IP_MULTICAST_TTL} option.
     */
    DatagramChannelConfig setTimeToLive(int ttl);

    /**
     * Gets the address of the network interface used for multicast packets.
     */
    InetAddress getInterface();

    /**
     * Sets the address of the network interface used for multicast packets.
     */
    DatagramChannelConfig setInterface(InetAddress interfaceAddress);

    /**
     * Gets the {@link StandardSocketOptions#IP_MULTICAST_IF} option.
     */
    NetworkInterface getNetworkInterface();

    /**
     * Sets the {@link StandardSocketOptions#IP_MULTICAST_IF} option.
     */
    DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface);

    @Override
    @Deprecated
    DatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead);

    @Override
    DatagramChannelConfig setWriteSpinCount(int writeSpinCount);

    @Override
    DatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis);

    @Override
    DatagramChannelConfig setAllocator(ByteBufAllocator allocator);

    @Override
    DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator);

    @Override
    DatagramChannelConfig setAutoRead(boolean autoRead);

    @Override
    DatagramChannelConfig setAutoClose(boolean autoClose);

    @Override
    DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);

    @Override
    DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

}
