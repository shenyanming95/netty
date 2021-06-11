package io.netty.channel.group;


import io.netty.channel.Channel;

/**
 * Allows to only match some {@link Channel}'s for operations in {@link ChannelGroup}.
 * <p>
 * {@link ChannelMatchers} provide you with helper methods for usual needed implementations.
 */
public interface ChannelMatcher {

    /**
     * Returns {@code true} if the operation should be also executed on the given {@link Channel}.
     */
    boolean matches(Channel channel);
}
