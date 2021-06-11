package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannelConfig;

/**
 * A {@link ChannelConfig} for a {@link OioSocketChannel}.
 *
 * <h3>Available options</h3>
 * <p>
 * In addition to the options provided by {@link SocketChannelConfig},
 * {@link OioSocketChannelConfig} allows the following options in the
 * option map:
 *
 * <table border="1" cellspacing="0" cellpadding="6">
 * <tr>
 * <th>Name</th><th>Associated setter method</th>
 * </tr><tr>
 * <td>{@link ChannelOption#SO_TIMEOUT}</td><td>{@link #setSoTimeout(int)}</td>
 * </tr>
 * </table>
 *
 * @deprecated use NIO / EPOLL / KQUEUE transport.
 */
@Deprecated
public interface OioSocketChannelConfig extends SocketChannelConfig {

    /**
     * Returns the maximal time a operation on the underlying socket may block.
     */
    int getSoTimeout();

    /**
     * Sets the maximal time a operation on the underlying socket may block.
     */
    OioSocketChannelConfig setSoTimeout(int timeout);

    @Override
    OioSocketChannelConfig setTcpNoDelay(boolean tcpNoDelay);

    @Override
    OioSocketChannelConfig setSoLinger(int soLinger);

    @Override
    OioSocketChannelConfig setSendBufferSize(int sendBufferSize);

    @Override
    OioSocketChannelConfig setReceiveBufferSize(int receiveBufferSize);

    @Override
    OioSocketChannelConfig setKeepAlive(boolean keepAlive);

    @Override
    OioSocketChannelConfig setTrafficClass(int trafficClass);

    @Override
    OioSocketChannelConfig setReuseAddress(boolean reuseAddress);

    @Override
    OioSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth);

    @Override
    OioSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure);

    @Override
    OioSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis);

    @Override
    @Deprecated
    OioSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead);

    @Override
    OioSocketChannelConfig setWriteSpinCount(int writeSpinCount);

    @Override
    OioSocketChannelConfig setAllocator(ByteBufAllocator allocator);

    @Override
    OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator);

    @Override
    OioSocketChannelConfig setAutoRead(boolean autoRead);

    @Override
    OioSocketChannelConfig setAutoClose(boolean autoClose);

    @Override
    OioSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark);

    @Override
    OioSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark);

    @Override
    OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override
    OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);
}
