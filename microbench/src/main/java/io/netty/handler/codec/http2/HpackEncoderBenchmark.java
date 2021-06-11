/*
 * Copyright 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Threads(1)
@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class HpackEncoderBenchmark extends AbstractMicrobenchmark {

    @Param
    public HpackHeadersSize size;

    @Param({"true", "false"})
    public boolean sensitive;

    @Param({"true", "false"})
    public boolean duplicates;

    @Param({"true", "false"})
    public boolean limitToAscii;

    private Http2Headers http2Headers;
    private ByteBuf output;
    private Http2HeadersEncoder.SensitivityDetector sensitivityDetector;

    @Setup(Level.Trial)
    public void setup() {
        http2Headers = HpackBenchmarkUtil.http2Headers(size, limitToAscii);
        if (duplicates) {
            int size = http2Headers.size();
            if (size > 0) {
                Iterator<Map.Entry<CharSequence, CharSequence>> itr = http2Headers.iterator();
                Map.Entry<CharSequence, CharSequence> entry = itr.next();
                http2Headers.clear();
                for (int i = 0; i < size; ++i) {
                    http2Headers.add(entry.getKey(), entry.getValue());
                }
            }
        }
        output = size.newOutBuffer();
        sensitivityDetector = sensitive ? Http2HeadersEncoder.ALWAYS_SENSITIVE : Http2HeadersEncoder.NEVER_SENSITIVE;
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        output.release();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void encode(Blackhole bh) throws Exception {
        HpackEncoder hpackEncoder = HpackUtilBenchmark.newTestEncoder();
        output.clear();
        hpackEncoder.encodeHeaders(3 /*randomly chosen*/, output, http2Headers, sensitivityDetector);
        bh.consume(output);
    }
}
