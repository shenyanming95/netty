/*
 * Copyright 2016 The Netty Project
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
package io.netty.microbench.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.*;
import io.netty.microbench.channel.EmbeddedChannelWriteReleaseHandlerContext;
import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Fork(1)
@Threads(1)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class HttpObjectEncoderBenchmark extends AbstractMicrobenchmark {
    @Param({"true", "false"})
    public boolean pooledAllocator;
    @Param({"true", "false"})
    public boolean voidPromise;
    private HttpRequestEncoder encoder;
    private FullHttpRequest fullRequest;
    private LastHttpContent lastContent;
    private HttpRequest contentLengthRequest;
    private HttpRequest chunkedRequest;
    private ByteBuf content;
    private ChannelHandlerContext context;

    @Setup(Level.Trial)
    public void setup() {
        byte[] bytes = new byte[256];
        content = Unpooled.buffer(bytes.length);
        content.writeBytes(bytes);
        ByteBuf testContent = Unpooled.unreleasableBuffer(content.asReadOnly());
        HttpHeaders headersWithChunked = new DefaultHttpHeaders(false);
        headersWithChunked.add(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
        HttpHeaders headersWithContentLength = new DefaultHttpHeaders(false);
        headersWithContentLength.add(HttpHeaderNames.CONTENT_LENGTH, testContent.readableBytes());

        fullRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/index", testContent, headersWithContentLength, EmptyHttpHeaders.INSTANCE);
        contentLengthRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/index", headersWithContentLength);
        chunkedRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/index", headersWithChunked);
        lastContent = new DefaultLastHttpContent(testContent, false);

        encoder = new HttpRequestEncoder();
        context = new EmbeddedChannelWriteReleaseHandlerContext(pooledAllocator ? PooledByteBufAllocator.DEFAULT : UnpooledByteBufAllocator.DEFAULT, encoder) {
            @Override
            protected void handleException(Throwable t) {
                handleUnexpectedException(t);
            }
        };
    }

    @TearDown(Level.Trial)
    public void teardown() {
        content.release();
        content = null;
    }

    @Benchmark
    public void fullMessage() throws Exception {
        encoder.write(context, fullRequest, newPromise());
    }

    @Benchmark
    public void contentLength() throws Exception {
        encoder.write(context, contentLengthRequest, newPromise());
        encoder.write(context, lastContent, newPromise());
    }

    @Benchmark
    public void chunked() throws Exception {
        encoder.write(context, chunkedRequest, newPromise());
        encoder.write(context, lastContent, newPromise());
    }

    private ChannelPromise newPromise() {
        return voidPromise ? context.voidPromise() : context.newPromise();
    }
}
