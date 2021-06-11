package io.netty.microbench.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.CharsetUtil;
import org.openjdk.jmh.annotations.*;

/**
 * This benchmark is based on HttpRequestDecoderTest class.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 20)
public class HttpRequestDecoderBenchmark extends AbstractMicrobenchmark {

    private static final int CONTENT_LENGTH = 120;
    private static final byte[] CONTENT_MIXED_DELIMITERS = createContent("\r\n", "\n");
    @Param({"2", "4", "8", "16", "32"})
    public int step;

    private static byte[] createContent(String... lineDelimiters) {
        String lineDelimiter;
        String lineDelimiter2;
        if (lineDelimiters.length == 2) {
            lineDelimiter = lineDelimiters[0];
            lineDelimiter2 = lineDelimiters[1];
        } else {
            lineDelimiter = lineDelimiters[0];
            lineDelimiter2 = lineDelimiters[0];
        }
        // This GET request is incorrect but it does not matter for HttpRequestDecoder.
        // It used only to get a long request.
        return ("GET /some/path?foo=bar&wibble=eek HTTP/1.1" + "\r\n" + "Upgrade: WebSocket" + lineDelimiter2 + "Connection: Upgrade" + lineDelimiter + "Host: localhost" + lineDelimiter2 + "Referer: http://www.site.ru/index.html" + lineDelimiter + "User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5" + lineDelimiter2 + "Accept: text/html" + lineDelimiter + "Cookie: income=1" + lineDelimiter2 + "Origin: http://localhost:8080" + lineDelimiter + "Sec-WebSocket-Key1: 10  28 8V7 8 48     0" + lineDelimiter2 + "Sec-WebSocket-Key2: 8 Xt754O3Q3QW 0   _60" + lineDelimiter + "Content-Type: application/x-www-form-urlencoded" + lineDelimiter2 + "Content-Length: " + CONTENT_LENGTH + lineDelimiter + "\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n" + "1234567890\r\n").getBytes(CharsetUtil.US_ASCII);
    }

    private static void testDecodeWholeRequestInMultipleSteps(byte[] content, int fragmentSize) {
        final EmbeddedChannel channel = new EmbeddedChannel(new HttpRequestDecoder());

        final int headerLength = content.length - CONTENT_LENGTH;

        // split up the header
        for (int a = 0; a < headerLength; ) {
            int amount = fragmentSize;
            if (a + amount > headerLength) {
                amount = headerLength - a;
            }

            // if header is done it should produce an HttpRequest
            channel.writeInbound(Unpooled.wrappedBuffer(content, a, amount).asReadOnly());
            a += amount;
        }

        for (int i = CONTENT_LENGTH; i > 0; i--) {
            // Should produce HttpContent
            channel.writeInbound(Unpooled.wrappedBuffer(content, content.length - i, 1).asReadOnly());
        }
    }

    @Benchmark
    public void testDecodeWholeRequestInMultipleStepsMixedDelimiters() {
        testDecodeWholeRequestInMultipleSteps(CONTENT_MIXED_DELIMITERS, step);
    }
}
