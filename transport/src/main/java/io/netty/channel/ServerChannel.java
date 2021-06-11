package io.netty.channel;

import io.netty.channel.socket.ServerSocketChannel;

/**
 * A {@link Channel} that accepts an incoming connection attempt and creates
 * its child {@link Channel}s by accepting them.  {@link ServerSocketChannel} is
 * a good example.
 */
public interface ServerChannel extends Channel {
    // This is a tag interface.
}
