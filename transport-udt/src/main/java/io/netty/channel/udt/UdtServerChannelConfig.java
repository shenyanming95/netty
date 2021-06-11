package io.netty.channel.udt;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.KindUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;

/**
 * A {@link ChannelConfig} for a {@link UdtServerChannel}.
 * <p>
 * Note that {@link TypeUDT#DATAGRAM} message oriented channels treat
 * {@code "receiveBufferSize"} and {@code "sendBufferSize"} as maximum message
 * size. If received or sent message does not fit specified sizes,
 * {@link ChannelException} will be thrown.
 *
 * @deprecated The UDT transport is no longer maintained and will be removed.
 */
@Deprecated
public interface UdtServerChannelConfig extends UdtChannelConfig {

    /**
     * Gets {@link KindUDT#ACCEPTOR} channel backlog via
     * {@link ChannelOption#SO_BACKLOG}.
     */
    int getBacklog();

    /**
     * Sets {@link KindUDT#ACCEPTOR} channel backlog via
     * {@link ChannelOption#SO_BACKLOG}.
     */
    UdtServerChannelConfig setBacklog(int backlog);

    @Override
    UdtServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis);

    @Override
    @Deprecated
    UdtServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead);

    @Override
    UdtServerChannelConfig setWriteSpinCount(int writeSpinCount);

    @Override
    UdtServerChannelConfig setAllocator(ByteBufAllocator allocator);

    @Override
    UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator);

    @Override
    UdtServerChannelConfig setAutoRead(boolean autoRead);

    @Override
    UdtServerChannelConfig setAutoClose(boolean autoClose);

    @Override
    UdtServerChannelConfig setProtocolReceiveBufferSize(int size);

    @Override
    UdtServerChannelConfig setProtocolSendBufferSize(int size);

    @Override
    UdtServerChannelConfig setReceiveBufferSize(int receiveBufferSize);

    @Override
    UdtServerChannelConfig setReuseAddress(boolean reuseAddress);

    @Override
    UdtServerChannelConfig setSendBufferSize(int sendBufferSize);

    @Override
    UdtServerChannelConfig setSoLinger(int soLinger);

    @Override
    UdtServerChannelConfig setSystemReceiveBufferSize(int size);

    @Override
    UdtServerChannelConfig setSystemSendBufferSize(int size);

    @Override
    UdtServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark);

    @Override
    UdtServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark);

    @Override
    UdtServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override
    UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);
}
