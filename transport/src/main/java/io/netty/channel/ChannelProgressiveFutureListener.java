package io.netty.channel;

import io.netty.util.concurrent.GenericProgressiveFutureListener;

import java.util.EventListener;

/**
 * An {@link EventListener} listener which will be called once the sending task associated with future is
 * being transferred.
 */
public interface ChannelProgressiveFutureListener extends GenericProgressiveFutureListener<ChannelProgressiveFuture> {
    // Just a type alias
}
