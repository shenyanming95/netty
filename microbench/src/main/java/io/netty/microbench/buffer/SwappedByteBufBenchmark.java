package io.netty.microbench.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

import java.nio.ByteOrder;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 25)
public class SwappedByteBufBenchmark extends AbstractMicrobenchmark {
    @Param("16384")
    public int size;
    private ByteBuf swappedByteBuf;
    private ByteBuf unsafeSwappedByteBuf;

    @Setup
    public void setup() {
        swappedByteBuf = new SwappedByteBuf(Unpooled.directBuffer(8));
        unsafeSwappedByteBuf = Unpooled.directBuffer(8).order(ByteOrder.LITTLE_ENDIAN);
        if (unsafeSwappedByteBuf.getClass().equals(SwappedByteBuf.class)) {
            throw new IllegalStateException("Should not use " + SwappedByteBuf.class.getSimpleName());
        }
    }

    @Benchmark
    public void swappedByteBufSetInt() {
        swappedByteBuf.setLong(0, size);
    }

    @Benchmark
    public void swappedByteBufSetShort() {
        swappedByteBuf.setShort(0, size);
    }

    @Benchmark
    public void swappedByteBufSetLong() {
        swappedByteBuf.setLong(0, size);
    }

    @Benchmark
    public void unsafeSwappedByteBufSetInt() {
        unsafeSwappedByteBuf.setInt(0, size);
    }

    @Benchmark
    public void unsafeSwappedByteBufSetShort() {
        unsafeSwappedByteBuf.setShort(0, size);
    }

    @Benchmark
    public void unsafeSwappedByteBufSetLong() {
        unsafeSwappedByteBuf.setLong(0, size);
    }
}
