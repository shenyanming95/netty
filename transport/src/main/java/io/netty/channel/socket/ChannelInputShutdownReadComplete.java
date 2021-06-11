package io.netty.channel.socket;

/**
 * User event that signifies the channel's input side is shutdown, and we tried to shut it down again. This typically
 * indicates that there is no more data to read.
 */
public final class ChannelInputShutdownReadComplete {
    public static final ChannelInputShutdownReadComplete INSTANCE = new ChannelInputShutdownReadComplete();

    private ChannelInputShutdownReadComplete() {
    }
}
