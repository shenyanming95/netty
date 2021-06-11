package io.netty.channel;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * {@link MultithreadEventLoopGroup} which must be used for the local transport.
 */
public class DefaultEventLoopGroup extends MultithreadEventLoopGroup {

    /**
     * Create a new instance with the default number of threads.
     */
    public DefaultEventLoopGroup() {
        this(0);
    }

    /**
     * Create a new instance
     *
     * @param nThreads the number of threads to use
     */
    public DefaultEventLoopGroup(int nThreads) {
        this(nThreads, (ThreadFactory) null);
    }

    /**
     * Create a new instance with the default number of threads and the given {@link ThreadFactory}.
     *
     * @param threadFactory the {@link ThreadFactory} or {@code null} to use the default
     */
    public DefaultEventLoopGroup(ThreadFactory threadFactory) {
        this(0, threadFactory);
    }

    /**
     * Create a new instance
     *
     * @param nThreads      the number of threads to use
     * @param threadFactory the {@link ThreadFactory} or {@code null} to use the default
     */
    public DefaultEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        super(nThreads, threadFactory);
    }

    /**
     * Create a new instance
     *
     * @param nThreads the number of threads to use
     * @param executor the Executor to use, or {@code null} if the default should be used.
     */
    public DefaultEventLoopGroup(int nThreads, Executor executor) {
        super(nThreads, executor);
    }

    @Override
    protected EventLoop newChild(Executor executor, Object... args) throws Exception {
        return new DefaultEventLoop(this, executor);
    }
}
