package io.netty.microbench.internal;

import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.internal.PlatformDependent;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Threads(1)
@State(Scope.Benchmark)
public class PlatformDependentBenchmark extends AbstractMicrobenchmark {

    @Param({"10", "50", "100", "1000", "10000", "100000"})
    private int size;
    private byte[] bytes1;
    private byte[] bytes2;

    @Setup(Level.Trial)
    public void setup() {
        bytes1 = new byte[size];
        bytes2 = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes1[i] = bytes2[i] = (byte) i;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean unsafeBytesEqual() {
        return PlatformDependent.equals(bytes1, 0, bytes2, 0, bytes1.length);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean arraysBytesEqual() {
        return Arrays.equals(bytes1, bytes2);
    }
}
