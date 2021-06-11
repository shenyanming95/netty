package io.netty.channel.pool;

/**
 * Allows to map {@link ChannelPool} implementations to a specific key.
 *
 * @param <K> the type of the key
 * @param <P> the type of the {@link ChannelPool}
 */
public interface ChannelPoolMap<K, P extends ChannelPool> {
    /**
     * Return the {@link ChannelPool} for the {@code code}. This will never return {@code null},
     * but create a new {@link ChannelPool} if non exists for they requested {@code key}.
     * <p>
     * Please note that {@code null} keys are not allowed.
     */
    P get(K key);

    /**
     * Returns {@code true} if a {@link ChannelPool} exists for the given {@code key}.
     * <p>
     * Please note that {@code null} keys are not allowed.
     */
    boolean contains(K key);
}
