package io.netty.channel;

import java.util.Map.Entry;

/**
 * {@link RecvByteBufAllocator} that limits a read operation based upon a maximum value per individual read
 * and a maximum amount when a read operation is attempted by the event loop.
 */
public interface MaxBytesRecvByteBufAllocator extends RecvByteBufAllocator {
    /**
     * Returns the maximum number of bytes to read per read loop.
     * a {@link ChannelInboundHandler#channelRead(ChannelHandlerContext, Object) channelRead()} event.
     * If this value is greater than 1, an event loop might attempt to read multiple times to procure bytes.
     */
    int maxBytesPerRead();

    /**
     * Sets the maximum number of bytes to read per read loop.
     * If this value is greater than 1, an event loop might attempt to read multiple times to procure bytes.
     */
    MaxBytesRecvByteBufAllocator maxBytesPerRead(int maxBytesPerRead);

    /**
     * Returns the maximum number of bytes to read per individual read operation.
     * a {@link ChannelInboundHandler#channelRead(ChannelHandlerContext, Object) channelRead()} event.
     * If this value is greater than 1, an event loop might attempt to read multiple times to procure bytes.
     */
    int maxBytesPerIndividualRead();

    /**
     * Sets the maximum number of bytes to read per individual read operation.
     * If this value is greater than 1, an event loop might attempt to read multiple times to procure bytes.
     */
    MaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int maxBytesPerIndividualRead);

    /**
     * Atomic way to get the maximum number of bytes to read for a read loop and per individual read operation.
     * If this value is greater than 1, an event loop might attempt to read multiple times to procure bytes.
     *
     * @return The Key is from {@link #maxBytesPerRead()}. The Value is from {@link #maxBytesPerIndividualRead()}
     */
    Entry<Integer, Integer> maxBytesPerReadPair();

    /**
     * Sets the maximum number of bytes to read for a read loop and per individual read operation.
     * If this value is greater than 1, an event loop might attempt to read multiple times to procure bytes.
     *
     * @param maxBytesPerRead           see {@link #maxBytesPerRead(int)}
     * @param maxBytesPerIndividualRead see {@link #maxBytesPerIndividualRead(int)}
     */
    MaxBytesRecvByteBufAllocator maxBytesPerReadPair(int maxBytesPerRead, int maxBytesPerIndividualRead);
}
