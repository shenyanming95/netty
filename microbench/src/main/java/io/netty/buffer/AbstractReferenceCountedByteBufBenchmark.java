package io.netty.buffer;

import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class AbstractReferenceCountedByteBufBenchmark extends AbstractMicrobenchmark {

    @Param({"1", "10", "100", "1000", "10000"})
    public int delay;

    AbstractReferenceCountedByteBuf buf;

    @Setup
    public void setUp() {
        buf = (AbstractReferenceCountedByteBuf) Unpooled.buffer(1);
    }

    @TearDown
    public void tearDown() {
        buf.release();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean retainReleaseUncontended() {
        buf.retain();
        Blackhole.consumeCPU(delay);
        return buf.release();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @GroupThreads(4)
    public boolean retainReleaseContended() {
        buf.retain();
        Blackhole.consumeCPU(delay);
        return buf.release();
    }
}
