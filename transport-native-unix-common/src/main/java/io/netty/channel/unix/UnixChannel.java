package io.netty.channel.unix;

import io.netty.channel.Channel;

/**
 * {@link Channel} that expose operations that are only present on {@code UNIX} like systems.
 */
public interface UnixChannel extends Channel {
    /**
     * Returns the {@link FileDescriptor} that is used by this {@link Channel}.
     */
    FileDescriptor fd();
}
