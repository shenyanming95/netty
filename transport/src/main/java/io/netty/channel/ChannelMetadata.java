package io.netty.channel;

import java.net.SocketAddress;

import static io.netty.util.internal.ObjectUtil.checkPositive;

/**
 * Represents the properties of a {@link Channel} implementation.
 */
public final class ChannelMetadata {

    private final boolean hasDisconnect;
    private final int defaultMaxMessagesPerRead;

    /**
     * Create a new instance
     *
     * @param hasDisconnect {@code true} if and only if the channel has the {@code disconnect()} operation
     *                      that allows a user to disconnect and then call {@link Channel#connect(SocketAddress)}
     *                      again, such as UDP/IP.
     */
    public ChannelMetadata(boolean hasDisconnect) {
        this(hasDisconnect, 1);
    }

    /**
     * Create a new instance
     *
     * @param hasDisconnect             {@code true} if and only if the channel has the {@code disconnect()} operation
     *                                  that allows a user to disconnect and then call {@link Channel#connect(SocketAddress)}
     *                                  again, such as UDP/IP.
     * @param defaultMaxMessagesPerRead If a {@link MaxMessagesRecvByteBufAllocator} is in use, then this value will be
     *                                  set for {@link MaxMessagesRecvByteBufAllocator#maxMessagesPerRead()}. Must be {@code > 0}.
     */
    public ChannelMetadata(boolean hasDisconnect, int defaultMaxMessagesPerRead) {
        checkPositive(defaultMaxMessagesPerRead, "defaultMaxMessagesPerRead");
        this.hasDisconnect = hasDisconnect;
        this.defaultMaxMessagesPerRead = defaultMaxMessagesPerRead;
    }

    /**
     * Returns {@code true} if and only if the channel has the {@code disconnect()} operation
     * that allows a user to disconnect and then call {@link Channel#connect(SocketAddress)} again,
     * such as UDP/IP.
     */
    public boolean hasDisconnect() {
        return hasDisconnect;
    }

    /**
     * If a {@link MaxMessagesRecvByteBufAllocator} is in use, then this is the default value for
     * {@link MaxMessagesRecvByteBufAllocator#maxMessagesPerRead()}.
     */
    public int defaultMaxMessagesPerRead() {
        return defaultMaxMessagesPerRead;
    }
}
