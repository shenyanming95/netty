package io.netty.microbench.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(5)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PooledByteBufAllocatorAlignBenchmark extends AbstractMicrobenchmark {

    private static final Random rand = new Random();

    /**
     * Cache line power of 2.
     */
    private static final int CACHE_LINE_MAX = 256;

    /**
     * PRNG to walk the chunk randomly to avoid streaming reads.
     */
    private static final int OFFSET_ADD = CACHE_LINE_MAX * 1337;

    /**
     * Block of bytes to write/read. (Corresponds to int type)
     */
    private static final int BLOCK = 4;

    @Param({"0", "64"})
    private int cacheAlign;

    @Param({"01024", "04096", "16384", "65536", "1048576"})
    private int size;

    private ByteBuf pooledDirectBuffer;

    private byte[] bytes;

    private int sizeMask;

    private int alignOffset;

    @Setup
    public void doSetup() {
        PooledByteBufAllocator pooledAllocator = new PooledByteBufAllocator(true, 4, 4, 8192, 11, 0, 0, 0, true, cacheAlign);
        pooledDirectBuffer = pooledAllocator.directBuffer(size + 64);
        sizeMask = size - 1;
        if (cacheAlign == 0) {
            long addr = pooledDirectBuffer.memoryAddress();
            // make sure address is miss-aligned
            if (addr % 64 == 0) {
                alignOffset = 63;
            }
            int off = 0;
            for (int c = 0; c < size; c++) {
                off = (off + OFFSET_ADD) & sizeMask;
                if ((addr + off + alignOffset) % BLOCK == 0) {
                    throw new IllegalStateException("Misaligned address is not really aligned");
                }
            }
        } else {
            alignOffset = 0;
            int off = 0;
            long addr = pooledDirectBuffer.memoryAddress();
            for (int c = 0; c < size; c++) {
                off = (off + OFFSET_ADD) & sizeMask;
                if ((addr + off) % BLOCK != 0) {
                    throw new IllegalStateException("Aligned address is not really aligned");
                }
            }
        }
        bytes = new byte[BLOCK];
        rand.nextBytes(bytes);
    }

    @TearDown
    public void doTearDown() {
        pooledDirectBuffer.release();
    }

    @Benchmark
    public void writeRead() {
        int off = 0;
        int lSize = size;
        int lSizeMask = sizeMask;
        int lAlignOffset = alignOffset;
        for (int i = 0; i < lSize; i++) {
            off = (off + OFFSET_ADD) & lSizeMask;
            pooledDirectBuffer.setBytes(off + lAlignOffset, bytes);
            pooledDirectBuffer.getBytes(off + lAlignOffset, bytes);
        }
    }

    @Benchmark
    public void write() {
        int off = 0;
        int lSize = size;
        int lSizeMask = sizeMask;
        int lAlignOffset = alignOffset;
        for (int i = 0; i < lSize; i++) {
            off = (off + OFFSET_ADD) & lSizeMask;
            pooledDirectBuffer.setBytes(off + lAlignOffset, bytes);
        }
    }

    @Benchmark
    public void read() {
        int off = 0;
        int lSize = size;
        int lSizeMask = sizeMask;
        int lAlignOffset = alignOffset;
        for (int i = 0; i < lSize; i++) {
            off = (off + OFFSET_ADD) & lSizeMask;
            pooledDirectBuffer.getBytes(off + lAlignOffset, bytes);
        }
    }
}
