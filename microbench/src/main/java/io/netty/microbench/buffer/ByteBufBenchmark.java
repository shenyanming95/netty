package io.netty.microbench.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;

import java.nio.ByteBuffer;

public class ByteBufBenchmark extends AbstractMicrobenchmark {
    private static final byte BYTE = '0';

    static {
        System.setProperty("io.netty.buffer.checkAccessible", "false");
    }

    @Param({"true", "false"})
    public String checkBounds;

    private ByteBuffer byteBuffer;
    private ByteBuffer directByteBuffer;
    private ByteBuf buffer;
    private ByteBuf directBuffer;
    private ByteBuf directBufferPooled;

    @Setup
    public void setup() {
        System.setProperty("io.netty.buffer.checkBounds", checkBounds);
        byteBuffer = ByteBuffer.allocate(8);
        directByteBuffer = ByteBuffer.allocateDirect(8);
        buffer = Unpooled.buffer(8);
        directBuffer = Unpooled.directBuffer(8);
        directBufferPooled = PooledByteBufAllocator.DEFAULT.directBuffer(8);
    }

    @TearDown
    public void tearDown() {
        buffer.release();
        directBuffer.release();
        directBufferPooled.release();
    }

    @Benchmark
    public ByteBuffer setByteBufferHeap() {
        return byteBuffer.put(0, BYTE);
    }

    @Benchmark
    public ByteBuffer setByteBufferDirect() {
        return directByteBuffer.put(0, BYTE);
    }

    @Benchmark
    public ByteBuf setByteBufHeap() {
        return buffer.setByte(0, BYTE);
    }

    @Benchmark
    public ByteBuf setByteBufDirect() {
        return directBuffer.setByte(0, BYTE);
    }

    @Benchmark
    public ByteBuf setByteBufDirectPooled() {
        return directBufferPooled.setByte(0, BYTE);
    }
}
