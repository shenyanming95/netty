package io.netty.microbench.buffer;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@Threads(8)
public class ByteBufAllocatorConcurrentBenchmark extends AbstractMicrobenchmark {

    private static final ByteBufAllocator unpooledAllocator = new UnpooledByteBufAllocator(true, true);

    @Param({"00064", "00256", "01024", "04096"})
    public int size;

    @Benchmark
    public boolean allocateRelease() {
        return unpooledAllocator.directBuffer(size).release();
    }
}
