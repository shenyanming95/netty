package io.netty.channel.unix;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

/**
 * Special {@link ChannelConfig} for {@link DomainSocketChannel}s.
 */
public interface DomainSocketChannelConfig extends ChannelConfig {

    @Override
    @Deprecated
    DomainSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead);

    @Override
    DomainSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis);

    @Override
    DomainSocketChannelConfig setWriteSpinCount(int writeSpinCount);

    @Override
    DomainSocketChannelConfig setAllocator(ByteBufAllocator allocator);

    @Override
    DomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator);

    @Override
    DomainSocketChannelConfig setAutoRead(boolean autoRead);

    @Override
    DomainSocketChannelConfig setAutoClose(boolean autoClose);

    @Override
    @Deprecated
    DomainSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark);

    @Override
    @Deprecated
    DomainSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark);

    @Override
    DomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override
    DomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator);

    /**
     * Return the {@link DomainSocketReadMode} for the channel.
     */
    DomainSocketReadMode getReadMode();

    /**
     * Change the {@link DomainSocketReadMode} for the channel. The default is
     * {@link DomainSocketReadMode#BYTES} which means bytes will be read from the
     * {@link io.netty.channel.Channel} and passed through the pipeline. If
     * {@link DomainSocketReadMode#FILE_DESCRIPTORS} is used
     * {@link FileDescriptor}s will be passed through the {@link io.netty.channel.ChannelPipeline}.
     * <p>
     * This setting can be modified on the fly if needed.
     */
    DomainSocketChannelConfig setReadMode(DomainSocketReadMode mode);
}
