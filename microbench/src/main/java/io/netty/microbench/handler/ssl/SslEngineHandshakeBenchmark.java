/*
 * Copyright 2017 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
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
