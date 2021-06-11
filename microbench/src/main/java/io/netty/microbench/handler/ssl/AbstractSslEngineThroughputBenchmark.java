package io.netty.microbench.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;

import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import java.nio.ByteBuffer;

public abstract class AbstractSslEngineThroughputBenchmark extends AbstractSslEngineBenchmark {

    @Param({"64", "128", "512", "1024", "4096"})
    public int messageSize;

    protected ByteBuffer wrapSrcBuffer;
    private ByteBuffer wrapDstBuffer;

    @Setup(Level.Iteration)
    public final void setup() throws Exception {
        ByteBufAllocator allocator = new PooledByteBufAllocator(true);
        initEngines(allocator);
        initHandshakeBuffers();

        wrapDstBuffer = allocateBuffer(clientEngine.getSession().getPacketBufferSize() << 2);
        wrapSrcBuffer = allocateBuffer(messageSize);

        byte[] bytes = new byte[messageSize];
        PlatformDependent.threadLocalRandom().nextBytes(bytes);
        wrapSrcBuffer.put(bytes);
        wrapSrcBuffer.flip();

        // Complete the initial TLS handshake.
        if (!doHandshake()) {
            throw new IllegalStateException();
        }
        doSetup();
    }

    protected void doSetup() throws Exception {
    }

    @TearDown(Level.Iteration)
    public final void tearDown() throws Exception {
        destroyEngines();
        destroyHandshakeBuffers();
        freeBuffer(wrapSrcBuffer);
        freeBuffer(wrapDstBuffer);
        doTearDown();
    }

    protected void doTearDown() throws Exception {
    }

    protected final ByteBuffer doWrap(int numWraps) throws SSLException {
        wrapDstBuffer.clear();

        for (int i = 0; i < numWraps; ++i) {
            wrapSrcBuffer.position(0).limit(messageSize);

            SSLEngineResult wrapResult = clientEngine.wrap(wrapSrcBuffer, wrapDstBuffer);

            assert checkSslEngineResult(wrapResult, wrapSrcBuffer, wrapDstBuffer);
        }
        return wrapDstBuffer;
    }
}
