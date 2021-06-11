package io.netty.channel.group;

import io.netty.util.concurrent.GenericFutureListener;

/**
 * Listens to the result of a {@link ChannelGroupFuture}.  The result of the
 * asynchronous {@link ChannelGroup} I/O operations is notified once this
 * listener is added by calling {@link ChannelGroupFuture#addListener(GenericFutureListener)}
 * and all I/O operations are complete.
 */
public interface ChannelGroupFutureListener extends GenericFutureListener<ChannelGroupFuture> {

}
