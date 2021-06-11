package io.netty.microbench.util;

import io.netty.channel.EventLoop;
import io.netty.util.concurrent.AbstractEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.openjdk.jmh.annotations.Fork;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNull;

/**
 * This harness facilitates the sharing of an executor between JMH and Netty and
 * thus avoid measuring context switching in microbenchmarks.
 */
@Fork(AbstractSharedExecutorMicrobenchmark.DEFAULT_FORKS)
public class AbstractSharedExecutorMicrobenchmark extends AbstractMicrobenchmarkBase {

    protected static final int DEFAULT_FORKS = 1;
    protected static final String[] JVM_ARGS;

    static {
        final String[] customArgs = {"-Xms2g", "-Xmx2g", "-XX:MaxDirectMemorySize=2g", "-Djmh.executor=CUSTOM", "-Djmh.executor.class=io.netty.microbench.util.AbstractSharedExecutorMicrobenchmark$DelegateHarnessExecutor"};

        JVM_ARGS = new String[BASE_JVM_ARGS.length + customArgs.length];
        System.arraycopy(BASE_JVM_ARGS, 0, JVM_ARGS, 0, BASE_JVM_ARGS.length);
        System.arraycopy(customArgs, 0, JVM_ARGS, BASE_JVM_ARGS.length, customArgs.length);
    }

    /**
     * Set the executor (in the form of an {@link EventLoop}) which JMH will use.
     * <p>
     * This must be called before JMH requires an executor to execute objects.
     *
     * @param eventLoop Used as an executor by JMH to run benchmarks.
     */
    public static void executor(EventLoop eventLoop) {
        DelegateHarnessExecutor.executor(eventLoop);
    }

    public static void handleUnexpectedException(Throwable t) {
        assertNull(t);
    }

    @Override
    protected String[] jvmArgs() {
        return JVM_ARGS;
    }

    /**
     * This executor allows Netty and JMH to share a common executor.
     * This is achieved by using {@link DelegateHarnessExecutor#executor(EventLoop)}
     * with the {@link EventLoop} used by Netty.
     */
    public static final class DelegateHarnessExecutor extends AbstractEventExecutor {
        private static EventLoop executor;
        private InternalLogger logger = InternalLoggerFactory.getInstance(DelegateHarnessExecutor.class);

        public DelegateHarnessExecutor(int maxThreads, String prefix) {
            logger.debug("Using DelegateHarnessExecutor executor {}", this);
        }

        /**
         * Set the executor (in the form of an {@link EventLoop}) which JMH will use.
         * <p>
         * This must be called before JMH requires an executor to execute objects.
         *
         * @param service Used as an executor by JMH to run benchmarks.
         */
        public static void executor(EventLoop service) {
            executor = service;
        }

        @Override
        public boolean inEventLoop() {
            return executor.inEventLoop();
        }

        @Override
        public boolean inEventLoop(Thread thread) {
            return executor.inEventLoop(thread);
        }

        @Override
        public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
            return executor.shutdownGracefully(quietPeriod, timeout, unit);
        }

        @Override
        public Future<?> terminationFuture() {
            return executor.terminationFuture();
        }

        @Override
        @Deprecated
        public void shutdown() {
            executor.shutdown();
        }

        @Override
        public boolean isShuttingDown() {
            return executor.isShuttingDown();
        }

        @Override
        public boolean isShutdown() {
            return executor.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return executor.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) {
            try {
                return executor.awaitTermination(timeout, unit);
            } catch (InterruptedException e) {
                handleUnexpectedException(e);
            }
            return false;
        }

        @Override
        public void execute(Runnable command) {
            executor.execute(command);
        }

        @Override
        public <V> Promise<V> newPromise() {
            return executor.newPromise();
        }

        @Override
        public <V> ProgressivePromise<V> newProgressivePromise() {
            return executor.newProgressivePromise();
        }
    }
}
