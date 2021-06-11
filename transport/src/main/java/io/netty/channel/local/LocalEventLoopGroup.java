package io.netty.channel.local;

import io.netty.channel.DefaultEventLoopGroup;

import java.util.concurrent.ThreadFactory;

/**
 * @deprecated Use {@link DefaultEventLoopGroup} instead.
 */
@Deprecated
public class LocalEventLoopGroup extends DefaultEventLoopGroup {

    /**
     * Create a new instance with the default number of threads.
     */
    public LocalEventLoopGroup() {
    }

    /**
     * Create a new instance
     *
     * @param nThreads the number of threads to use
     */
    public LocalEventLoopGroup(int nThreads) {
        super(nThreads);
    }

    /**
     * Create a new instance with the default number of threads and the given {@link ThreadFactory}.
     *
     * @param threadFactory the {@link ThreadFactory} or {@code null} to use the default
     */
    public LocalEventLoopGroup(ThreadFactory threadFactory) {
        super(0, threadFactory);
    }

    /**
     * Create a new instance
     *
     * @param nThreads      the number of threads to use
     * @param threadFactory the {@link ThreadFactory} or {@code null} to use the default
     */
    public LocalEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        super(nThreads, threadFactory);
    }
}
