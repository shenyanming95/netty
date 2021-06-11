package io.netty.microbench.internal;

import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.internal.PlatformDependent;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class UnitializedArrayBenchmark extends AbstractMicrobenchmark {

    @Param({"1", "10", "100", "1000", "10000", "100000"})
    private int size;

    @Setup(Level.Trial)
    public void setupTrial() {
        if (PlatformDependent.javaVersion() < 9) {
            throw new IllegalStateException("Needs Java9");
        }
        if (!PlatformDependent.hasUnsafe()) {
            throw new IllegalStateException("Needs Unsafe");
        }
    }

    @Override
    protected String[] jvmArgs() {
        // Ensure we minimize the GC overhead for this benchmark and also open up required package.
        // See also https://shipilev.net/jvm-anatomy-park/7-initialization-costs/
        return new String[]{"-XX:+UseParallelOldGC", "-Xmx8g", "-Xms8g", "-Xmn6g", "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED"};
    }

    @Benchmark
    public byte[] allocateInitializedByteArray() {
        return new byte[size];
    }

    @Benchmark
    public byte[] allocateUninitializedByteArray() {
        return PlatformDependent.allocateUninitializedArray(size);
    }
}
