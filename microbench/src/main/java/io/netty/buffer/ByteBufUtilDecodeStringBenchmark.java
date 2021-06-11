package io.netty.buffer;

import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
public class ByteBufUtilDecodeStringBenchmark extends AbstractMicrobenchmark {

    @Param({"8", "64", "1024", "10240", "1073741824"})
    public int size;
    @Param({"US-ASCII", "UTF-8"})
    public String charsetName;
    @Param
    public ByteBufType bufferType;
    private ByteBuf buffer;
    private Charset charset;

    @Override
    protected String[] jvmArgs() {
        // Ensure we minimize the GC overhead by sizing the heap big enough.
        return new String[]{"-XX:MaxDirectMemorySize=2g", "-Xmx8g", "-Xms8g", "-Xmn6g"};
    }

    @Setup
    public void setup() {
        byte[] bytes = new byte[size + 2];
        Arrays.fill(bytes, (byte) 'a');

        // Use an offset to not allow any optimizations because we use the exact passed in byte[] for heap buffers.
        buffer = bufferType.newBuffer(bytes, size);
        charset = Charset.forName(charsetName);
    }

    @TearDown
    public void teardown() {
        buffer.release();
    }

    @Benchmark
    public String decodeString() {
        return ByteBufUtil.decodeString(buffer, buffer.readerIndex(), size, charset);
    }

    public enum ByteBufType {
        DIRECT {
            @Override
            ByteBuf newBuffer(byte[] bytes, int length) {
                ByteBuf buffer = Unpooled.directBuffer(length);
                buffer.writeBytes(bytes, 0, length);
                return buffer;
            }
        }, HEAP_OFFSET {
            @Override
            ByteBuf newBuffer(byte[] bytes, int length) {
                return Unpooled.wrappedBuffer(bytes, 1, length);
            }
        }, HEAP {
            @Override
            ByteBuf newBuffer(byte[] bytes, int length) {
                return Unpooled.wrappedBuffer(bytes, 0, length);
            }
        }, COMPOSITE {
            @Override
            ByteBuf newBuffer(byte[] bytes, int length) {
                CompositeByteBuf buffer = Unpooled.compositeBuffer();
                int offset = 0;
                // 8 buffers per composite.
                int capacity = length / 8;

                while (length > 0) {
                    buffer.addComponent(true, Unpooled.wrappedBuffer(bytes, offset, Math.min(length, capacity)));
                    length -= capacity;
                    offset += capacity;
                }
                return buffer;
            }
        };

        abstract ByteBuf newBuffer(byte[] bytes, int length);
    }
}
