package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

/**
 * The {@link CompleteChannelFuture} which is failed already.  It is
 * recommended to use {@link Channel#newFailedFuture(Throwable)}
 * instead of calling the constructor of this future.
 */
final class FailedChannelFuture extends CompleteChannelFuture {

    private final Throwable cause;

    /**
     * Creates a new instance.
     *
     * @param channel the {@link Channel} associated with this future
     * @param cause   the cause of failure
     */
    FailedChannelFuture(Channel channel, EventExecutor executor, Throwable cause) {
        super(channel, executor);
        this.cause = ObjectUtil.checkNotNull(cause, "cause");
    }

    @Override
    public Throwable cause() {
        return cause;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public ChannelFuture sync() {
        PlatformDependent.throwException(cause);
        return this;
    }

    @Override
    public ChannelFuture syncUninterruptibly() {
        PlatformDependent.throwException(cause);
        return this;
    }
}
