package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DatagramChannelConfig;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @deprecated use NIO / EPOLL / KQUEUE transport.
 */
@Deprecated
public interface OioDatagramChannelConfig extends DatagramChannelConfig {
    /**
     * Returns the maximal time a operation on the underlying socket may block.
     */
    int getSoTimeout();

    /**
     * Sets the maximal time a operation on the underlying socket may block.
     */
    OioDatagramChannelConfig setSoTimeout(int timeout);

    @Override
    OioDatagramChannelConfig setSendBufferSize(int sendBufferSize);

    @Override
    OioDatagramChannelConfig setReceiveBufferSize(int receiveBufferSize);

    @Override
    OioDatagramChannelConfig setTrafficClass(int trafficClass);

    @Override
    OioDatagramChannelConfig setReuseAddress(boolean reuseAddress);

    @Override
    OioDatagramChannelConfig setBroadcast(boolean broadcast);

    @Override
    OioDatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled);

    @Override
    OioDatagramChannelConfig setTimeToLive(int ttl);

    @Override
    OioDatagramChannelConfig setInterface(InetAddress interfaceAddress);

    @Override
    OioDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface);

    @Override
    OioDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead);

    @Override
    OioDatagramChannelConfig setWriteSpinCount(int writeSpinCount);

    @Override
    OioDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis);

    @Override
    OioDatagramChannelConfig setAllocator(ByteBufAllocator allocator);

    @Override
    OioDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator);

    @Override
    OioDatagramChannelConfig setAutoRead(boolean autoRead);

    @Override
    OioDatagramChannelConfig setAutoClose(boolean autoClose);

    @Override
    OioDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);

    @Override
    OioDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override
    OioDatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark);

    @Override
    OioDatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark);
}
