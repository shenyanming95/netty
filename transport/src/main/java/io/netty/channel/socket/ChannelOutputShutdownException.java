package io.netty.channel.socket;

import io.netty.util.internal.UnstableApi;

import java.io.IOException;

/**
 * Used to fail pending writes when a channel's output has been shutdown.
 */
@UnstableApi
public final class ChannelOutputShutdownException extends IOException {
    private static final long serialVersionUID = 6712549938359321378L;

    public ChannelOutputShutdownException(String msg) {
        super(msg);
    }

    public ChannelOutputShutdownException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
