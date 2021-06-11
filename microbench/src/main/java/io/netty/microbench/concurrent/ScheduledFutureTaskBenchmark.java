package io.netty.microbench.concurrent;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoop;
import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ScheduledFutureTaskBenchmark extends AbstractMicrobenchmark {

    static final EventLoop executor = new DefaultEventLoop();

    @TearDown(Level.Trial)
    public void stop() throws Exception {
        executor.shutdownGracefully().syncUninterruptibly();
    }

    @Benchmark
    public Future<?> cancelInOrder(final FuturesHolder futuresHolder) {
        return executor.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < futuresHolder.num; i++) {
                    futuresHolder.futures.get(i).cancel(false);
                }
            }
        }).syncUninterruptibly();
    }

    @Benchmark
    public Future<?> cancelInReverseOrder(final FuturesHolder futuresHolder) {
        return executor.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = futuresHolder.num - 1; i >= 0; i--) {
                    futuresHolder.futures.get(i).cancel(false);
                }
            }
        }).syncUninterruptibly();
    }

    @State(Scope.Thread)
    public static class FuturesHolder {

        private static final Callable<Void> NO_OP = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return null;
            }
        };
        final List<ScheduledFuture<Void>> futures = new ArrayList<ScheduledFuture<Void>>();
        @Param({"100", "1000", "10000", "100000"})
        int num;

        @Setup(Level.Invocation)
        public void reset() {
            futures.clear();
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= num; i++) {
                        futures.add(executor.schedule(NO_OP, i, TimeUnit.HOURS));
                    }
                }
            }).syncUninterruptibly();
        }
    }
}
