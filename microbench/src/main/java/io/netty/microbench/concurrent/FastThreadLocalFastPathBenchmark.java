package io.netty.microbench.concurrent;

import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.concurrent.FastThreadLocal;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;

/**
 * This class benchmarks the fast path of FastThreadLocal and the JDK ThreadLocal.
 */
@Threads(4)
@Measurement(iterations = 10, batchSize = 100)
public class FastThreadLocalFastPathBenchmark extends AbstractMicrobenchmark {

    private static final Random rand = new Random();

    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Integer>[] jdkThreadLocals = new ThreadLocal[128];
    @SuppressWarnings("unchecked")
    private static final FastThreadLocal<Integer>[] fastThreadLocals = new FastThreadLocal[jdkThreadLocals.length];

    static {
        for (int i = 0; i < jdkThreadLocals.length; i++) {
            final int num = rand.nextInt();
            jdkThreadLocals[i] = new ThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    return num;
                }
            };
            fastThreadLocals[i] = new FastThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    return num;
                }
            };
        }
    }

    @Benchmark
    public void jdkThreadLocalGet(Blackhole bh) {
        for (ThreadLocal<Integer> i : jdkThreadLocals) {
            bh.consume(i.get());
        }
    }

    @Benchmark
    public void fastThreadLocal(Blackhole bh) {
        for (FastThreadLocal<Integer> i : fastThreadLocals) {
            bh.consume(i.get());
        }
    }
}
