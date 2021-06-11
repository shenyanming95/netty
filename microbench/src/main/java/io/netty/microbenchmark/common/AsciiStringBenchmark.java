package io.netty.microbenchmark.common;

import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;
import org.openjdk.jmh.annotations.*;

import java.util.Random;

@Threads(1)
@Measurement(iterations = 5)
@Warmup(iterations = 5)
public class AsciiStringBenchmark extends AbstractMicrobenchmark {

    private static final Random random = new Random();
    @Param({"3", "5", "7", "8", "10", "20", "50", "100", "1000"})
    public int size;
    private AsciiString asciiString;
    private String string;

    @Setup(Level.Trial)
    public void setup() {
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        asciiString = new AsciiString(bytes, false);
        string = new String(bytes, CharsetUtil.US_ASCII);
    }

    @Benchmark
    public int hashCodeBenchBytesOld() {
        int h = 0;
        final int end = asciiString.arrayOffset() + asciiString.length();
        for (int i = asciiString.arrayOffset(); i < end; ++i) {
            // masking with 0x1F reduces the number of overall bits that impact the hash code but makes the hash
            // code the same regardless of character case (upper case or lower case hash is the same).
            h = h * 31 + (asciiString.array()[i] & 0x1F);
        }
        return h;
    }

    @Benchmark
    public int hashCodeBenchBytesNew() {
        return PlatformDependent.hashCodeAscii(asciiString.array(), asciiString.arrayOffset(), asciiString.length());
    }

    @Benchmark
    public int hashCodeBenchCharSequenceOld() {
        int h = 0;
        for (int i = 0; i < string.length(); ++i) {
            // masking with 0x1F reduces the number of overall bits that impact the hash code but makes the hash
            // code the same regardless of character case (upper case or lower case hash is the same).
            h = h * 31 + (string.charAt(i) & 0x1F);
        }
        return h;
    }

    @Benchmark
    public int hashCodeBenchCharSequenceNew() {
        return PlatformDependent.hashCodeAscii(string);
    }
}
