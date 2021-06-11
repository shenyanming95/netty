package io.netty.channel.pool;

import io.netty.channel.Channel;

/**
 * A skeletal {@link ChannelPoolHandler} implementation.
 */
public abstract class AbstractChannelPoolHandler implements ChannelPoolHandler {

    /**
     * NOOP implementation, sub-classes may override this.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void channelAcquired(@SuppressWarnings("unused") Channel ch) throws Exception {
        // NOOP
    }

    /**
     * NOOP implementation, sub-classes may override this.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void channelReleased(@SuppressWarnings("unused") Channel ch) throws Exception {
        // NOOP
    }
}
