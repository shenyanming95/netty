package io.netty.microbench.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
public class SslEngineHandshakeBenchmark extends AbstractSslEngineBenchmark {

    private ByteBufAllocator allocator;

    @Setup(Level.Iteration)
    public void setup() {
        allocator = new PooledByteBufAllocator(true);
        // Init an engine one time and create the buffers needed for an handshake so we can use them in the benchmark
        initEngines(allocator);
        initHandshakeBuffers();
        destroyEngines();
    }

    @TearDown(Level.Iteration)
    public void teardown() {
        destroyHandshakeBuffers();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean handshake() throws Exception {
        initEngines(allocator);
        boolean ok = doHandshake();
        destroyEngines();
        assert ok;
        return ok;
    }
}
