package io.netty.buffer;

import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.netty.buffer.Unpooled.wrappedBuffer;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 12, time = 1, timeUnit = TimeUnit.SECONDS)
public class CompositeByteBufWriteOutBenchmark extends AbstractMicrobenchmark {

    @Param({"64", "1024", "10240", "102400", "1024000"})
    public int size;
    @Param
    public ByteBufType bufferType;
    private ByteBuf targetBuffer;
    private ByteBuf[] sourceBufs;

    private static ByteBuf[] makeSmallChunks(int length) {

        List<ByteBuf> buffers = new ArrayList<ByteBuf>(((length + 1) / 48) * 9);
        for (int i = 0; i < length + 48; i += 48) {
            for (int j = 4; j <= 12; j++) {
                buffers.add(wrappedBuffer(new byte[j]));
            }
        }

        return buffers.toArray(new ByteBuf[0]);
    }

    private static ByteBuf[] makeLargeChunks(int length) {

        List<ByteBuf> buffers = new ArrayList<ByteBuf>((length + 1) / 768);
        for (int i = 0; i < length + 1536; i += 1536) {
            buffers.add(wrappedBuffer(new byte[512]));
            buffers.add(wrappedBuffer(new byte[1024]));
        }

        return buffers.toArray(new ByteBuf[0]);
    }

    @Override
    protected String[] jvmArgs() {
        // Ensure we minimize the GC overhead by sizing the heap big enough.
        return new String[]{"-XX:MaxDirectMemorySize=2g", "-Xmx4g", "-Xms4g", "-Xmn3g"};
    }

    @Setup
    public void setup() {
        targetBuffer = PooledByteBufAllocator.DEFAULT.directBuffer(size + 2048);
        sourceBufs = bufferType.sourceBuffers(size);
    }

    @TearDown
    public void teardown() {
        targetBuffer.release();
    }

    @Benchmark
    public int writeCBB() {
        ByteBuf cbb = Unpooled.wrappedBuffer(Integer.MAX_VALUE, sourceBufs); // CompositeByteBuf
        return targetBuffer.clear().writeBytes(cbb).readableBytes();
    }

    @Benchmark
    public int writeFCBB() {
        ByteBuf cbb = Unpooled.wrappedUnmodifiableBuffer(sourceBufs); // FastCompositeByteBuf
        return targetBuffer.clear().writeBytes(cbb).readableBytes();
    }

    public enum ByteBufType {
        SMALL_CHUNKS {
            @Override
            ByteBuf[] sourceBuffers(int length) {
                return makeSmallChunks(length);
            }
        }, LARGE_CHUNKS {
            @Override
            ByteBuf[] sourceBuffers(int length) {
                return makeLargeChunks(length);
            }
        };

        abstract ByteBuf[] sourceBuffers(int length);
    }
}
