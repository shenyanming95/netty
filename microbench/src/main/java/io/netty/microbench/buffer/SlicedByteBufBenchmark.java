package io.netty.microbench.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;


@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 25)
public class SlicedByteBufBenchmark extends AbstractMicrobenchmark {

    private ByteBuf slicedByteBuf;
    private ByteBuf slicedAbstractByteBuf;
    private String ascii;

    @Setup
    public void setup() {
        // Use buffer sizes that will also allow to write UTF-8 without grow the buffer
        ByteBuf buffer = Unpooled.buffer(512).retain();
        slicedByteBuf = buffer.slice(0, 256);
        slicedAbstractByteBuf = buffer.slice(0, 256);

        if (slicedByteBuf.getClass() == slicedAbstractByteBuf.getClass()) {
            throw new IllegalStateException();
        }

        StringBuilder asciiSequence = new StringBuilder(128);
        for (int i = 0; i < 128; i++) {
            asciiSequence.append('a');
        }
        ascii = asciiSequence.toString();
    }

    @TearDown
    public void tearDown() {
        slicedByteBuf.release();
        slicedAbstractByteBuf.release();
    }

    @Benchmark
    public void writeAsciiStringSlice() {
        slicedByteBuf.resetWriterIndex();
        ByteBufUtil.writeAscii(slicedByteBuf, ascii);
    }

    @Benchmark
    public void writeAsciiStringSliceAbstract() {
        slicedAbstractByteBuf.resetWriterIndex();
        ByteBufUtil.writeAscii(slicedAbstractByteBuf, ascii);
    }
}
