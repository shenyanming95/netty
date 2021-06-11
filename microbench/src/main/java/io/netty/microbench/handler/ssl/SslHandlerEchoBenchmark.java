package io.netty.microbench.handler.ssl;

import io.netty.buffer.ByteBuf;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

public class SslHandlerEchoBenchmark extends AbstractSslHandlerThroughputBenchmark {
    @Param({"1", "2", "5", "10"})
    public int numWrites;

    @Benchmark
    public ByteBuf wrapUnwrap() throws Exception {
        ByteBuf src = doWrite(numWrites);

        do {
            serverSslHandler.channelRead(serverCtx, src);
        } while (src.isReadable());

        assert !src.isReadable() && src.refCnt() == 1 : "src: " + src + " src.refCnt(): " + src.refCnt();

        return src;
    }
}
