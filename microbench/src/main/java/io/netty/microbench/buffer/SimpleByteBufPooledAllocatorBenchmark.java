package io.netty.microbench.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SimpleByteBufPooledAllocatorBenchmark extends AbstractMicrobenchmark {

    @Param({"123", "1234", "12345", "123456", "1234567"})
    public int size;
    @Param({"0", "5", "10", "100"})
    public long tokens;
    @Param({"false", "true"})
    public boolean useThreadCache;
    public ByteBufAllocator allocator;

    public SimpleByteBufPooledAllocatorBenchmark() {
        super(true, false);
    }

    @Setup(Level.Trial)
    public void doSetup() {
        allocator = new PooledByteBufAllocator(PooledByteBufAllocator.defaultPreferDirect(), PooledByteBufAllocator.defaultNumHeapArena(), PooledByteBufAllocator.defaultNumDirectArena(), PooledByteBufAllocator.defaultPageSize(), PooledByteBufAllocator.defaultMaxOrder(), PooledByteBufAllocator.defaultTinyCacheSize(), PooledByteBufAllocator.defaultSmallCacheSize(), PooledByteBufAllocator.defaultNormalCacheSize(), useThreadCache);
    }

    @Benchmark
    public boolean getAndRelease() {
        ByteBuf buf = allocator.directBuffer(size);
        if (tokens > 0) {
            Blackhole.consumeCPU(tokens);
        }
        return buf.release();
    }
}
