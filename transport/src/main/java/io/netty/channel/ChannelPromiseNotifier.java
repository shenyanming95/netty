package io.netty.channel;

import io.netty.util.concurrent.PromiseNotifier;

/**
 * ChannelFutureListener implementation which takes other {@link ChannelPromise}(s) and notifies them on completion.
 */
public final class ChannelPromiseNotifier extends PromiseNotifier<Void, ChannelFuture> implements ChannelFutureListener {

    /**
     * Create a new instance
     *
     * @param promises the {@link ChannelPromise}s to notify once this {@link ChannelFutureListener} is notified.
     */
    public ChannelPromiseNotifier(ChannelPromise... promises) {
        super(promises);
    }

    /**
     * Create a new instance
     *
     * @param logNotifyFailure {@code true} if logging should be done in case notification fails.
     * @param promises         the {@link ChannelPromise}s to notify once this {@link ChannelFutureListener} is notified.
     */
    public ChannelPromiseNotifier(boolean logNotifyFailure, ChannelPromise... promises) {
        super(logNotifyFailure, promises);
    }
}
