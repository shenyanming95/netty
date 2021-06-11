package io.netty.buffer;

import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.netty.buffer.Unpooled.EMPTY_BUFFER;
import static io.netty.buffer.Unpooled.wrappedBuffer;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
public class CompositeByteBufRandomAccessBenchmark extends AbstractMicrobenchmark {

    @Param({"64", "10240", "1024000"}) // ({ "64", "1024", "10240", "102400", "1024000" })
    public int size;
    @Param
    public ByteBufType bufferType;
    private ByteBuf buffer;
    private Random random;

    private static ByteBuf newBufferSmallChunks(int length) {

        List<ByteBuf> buffers = new ArrayList<ByteBuf>(((length + 1) / 45) * 19);
        for (int i = 0; i < length + 45; i += 45) {
            for (int j = 1; j <= 9; j++) {
                buffers.add(EMPTY_BUFFER);
                buffers.add(wrappedBuffer(new byte[j]));
            }
            buffers.add(EMPTY_BUFFER);
        }

        ByteBuf buffer = wrappedBuffer(Integer.MAX_VALUE, buffers.toArray(new ByteBuf[0]));

        // Truncate to the requested capacity.
        return buffer.capacity(length).writerIndex(0);
    }

    private static ByteBuf newBufferLargeChunks(int length) {

        List<ByteBuf> buffers = new ArrayList<ByteBuf>((length + 1) / 512);
        for (int i = 0; i < length + 1536; i += 1536) {
            buffers.add(wrappedBuffer(new byte[512]));
            buffers.add(EMPTY_BUFFER);
            buffers.add(wrappedBuffer(new byte[1024]));
        }

        ByteBuf buffer = wrappedBuffer(Integer.MAX_VALUE, buffers.toArray(new ByteBuf[0]));

        // Truncate to the requested capacity.
        return buffer.capacity(length).writerIndex(0);
    }

    @Setup
    public void setup() {
        buffer = bufferType.newBuffer(size);
        random = new Random(0L);
    }

    @TearDown
    public void teardown() {
        buffer.release();
    }

    @Benchmark
    public long setGetLong() {
        int i = random.nextInt(size - 8);
        return buffer.setLong(i, 1).getLong(i);
    }

    @Benchmark
    public ByteBuf setLong() {
        int i = random.nextInt(size - 8);
        return buffer.setLong(i, 1);
    }

    public enum ByteBufType {
        SMALL_CHUNKS {
            @Override
            ByteBuf newBuffer(int length) {
                return newBufferSmallChunks(length);
            }
        }, LARGE_CHUNKS {
            @Override
            ByteBuf newBuffer(int length) {
                return newBufferLargeChunks(length);
            }
        };

        abstract ByteBuf newBuffer(int length);
    }
}
