package io.netty.channel;

import io.netty.util.concurrent.AbstractEventExecutorGroup;

/**
 * Skeletal implementation of {@link EventLoopGroup}.
 */
public abstract class AbstractEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
    @Override
    public abstract EventLoop next();
}
