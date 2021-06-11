package io.netty.microbench.handler.ssl;

import org.openjdk.jmh.annotations.*;

import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import java.nio.ByteBuffer;

@State(Scope.Benchmark)
@Threads(1)
public class SslEngineEchoBenchmark extends AbstractSslEngineThroughputBenchmark {

    @Param({"1", "2", "5", "10"})
    public int numWraps;
    private ByteBuffer unwrapDstBuffer;

    @Override
    protected void doSetup() {
        unwrapDstBuffer = allocateBuffer(serverEngine.getSession().getApplicationBufferSize() << 2);
    }

    @Override
    protected void doTearDown() {
        freeBuffer(unwrapDstBuffer);
    }

    @Benchmark
    public ByteBuffer wrapUnwrap() throws SSLException {
        ByteBuffer src = doWrap(numWraps);
        src.flip();

        unwrapDstBuffer.clear();

        SSLEngineResult unwrapResult;
        do {
            unwrapResult = serverEngine.unwrap(src, unwrapDstBuffer);
        } while (unwrapResult.getStatus() == SSLEngineResult.Status.OK && src.hasRemaining());

        assert checkSslEngineResult(unwrapResult, src, unwrapDstBuffer);
        return unwrapDstBuffer;
    }
}
