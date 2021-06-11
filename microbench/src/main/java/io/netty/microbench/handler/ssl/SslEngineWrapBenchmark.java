package io.netty.microbench.handler.ssl;

import org.openjdk.jmh.annotations.*;

import javax.net.ssl.SSLException;
import java.nio.ByteBuffer;

@State(Scope.Benchmark)
@Threads(1)
public class SslEngineWrapBenchmark extends AbstractSslEngineThroughputBenchmark {

    @Param({"1", "2", "5", "10"})
    public int numWraps;

    @Benchmark
    public ByteBuffer wrap() throws SSLException {
        return doWrap(numWraps);
    }
}
