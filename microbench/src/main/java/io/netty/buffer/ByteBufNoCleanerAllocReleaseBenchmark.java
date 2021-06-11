package io.netty.buffer;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@Threads(16)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class ByteBufNoCleanerAllocReleaseBenchmark extends AbstractByteBufNoCleanerBenchmark {

    @Param({"64", "1024", "8192"})
    public int initialCapacity;

    @Benchmark
    public boolean allocateRelease() {
        ByteBuf buffer = bufferType.newBuffer(initialCapacity);
        return buffer.release();
    }
}
