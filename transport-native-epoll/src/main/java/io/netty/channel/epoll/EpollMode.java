package io.netty.channel.epoll;

/**
 * The <a href="http://linux.die.net/man/7/epoll">epoll</a> mode to use.
 */
public enum EpollMode {

    /**
     * Use {@code EPOLLET} (edge-triggered).
     *
     * @see <a href="http://linux.die.net/man/7/epoll">man 7 epoll</a>.
     */
    EDGE_TRIGGERED,

    /**
     * Do not use {@code EPOLLET} (level-triggered).
     *
     * @see <a href="http://linux.die.net/man/7/epoll">man 7 epoll</a>.
     */
    LEVEL_TRIGGERED
}
