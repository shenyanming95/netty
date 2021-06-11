package io.netty.buffer;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@Threads(16)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class ByteBufNoCleanerChangeCapacityBenchmark extends AbstractByteBufNoCleanerBenchmark {
    private static final int MAX_DIRECT_MEMORY_PER_THREAD = 1024 * 1024; // 1 mb per thread.

    @Param("1024")
    public int initialCapacity;

    @Benchmark
    public boolean capacityChange() {
        ByteBuf buffer = bufferType.newBuffer(initialCapacity);
        // Change capacity until we would exceed the 1mb per thread limit
        for (int newCapacity = initialCapacity << 1; newCapacity <= MAX_DIRECT_MEMORY_PER_THREAD; newCapacity += initialCapacity) {
            buffer.capacity(newCapacity);
        }
        return buffer.release();
    }
}
