package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.ServerSocketChannelConfig;


/**
 * A {@link ServerSocketChannelConfig} for a {@link OioServerSocketChannel}.
 *
 * <h3>Available options</h3>
 * <p>
 * In addition to the options provided by {@link ServerSocketChannelConfig},
 * {@link OioServerSocketChannelConfig} allows the following options in the
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
public interface OioServerSocketChannelConfig extends ServerSocketChannelConfig {

    /**
     * Returns the maximal time a operation on the underlying socket may block.
     */
    int getSoTimeout();

    /**
     * Sets the maximal time a operation on the underlying socket may block.
     */
    OioServerSocketChannelConfig setSoTimeout(int timeout);

    @Override
    OioServerSocketChannelConfig setBacklog(int backlog);

    @Override
    OioServerSocketChannelConfig setReuseAddress(boolean reuseAddress);

    @Override
    OioServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize);

    @Override
    OioServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth);

    @Override
    OioServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis);

    @Override
    @Deprecated
    OioServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead);

    @Override
    OioServerSocketChannelConfig setWriteSpinCount(int writeSpinCount);

    @Override
    OioServerSocketChannelConfig setAllocator(ByteBufAllocator allocator);

    @Override
    OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator);

    @Override
    OioServerSocketChannelConfig setAutoRead(boolean autoRead);

    @Override
    OioServerSocketChannelConfig setAutoClose(boolean autoClose);

    @Override
    OioServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark);

    @Override
    OioServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark);

    @Override
    OioServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override
    OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);
}
