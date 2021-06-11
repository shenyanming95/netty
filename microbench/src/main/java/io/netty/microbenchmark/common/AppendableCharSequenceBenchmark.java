/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License, version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.netty.microbenchmark.common;

import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.Random;

@Threads(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class AppendableCharSequenceBenchmark extends AbstractMicrobenchmark {
    private static final Random rand = new Random();
    @Param({"32", "64", "128", "256"})
    private int charsInitSize;
    @Param({"10", "100", "10000", "1000000"})
    private int simulatedDataSize;
    private char[] chars;
    private char simulatedData;
    private int pos;

    @Setup(Level.Trial)
    public void setup() {
        chars = new char[charsInitSize];
        simulatedData = (char) rand.nextInt();
    }

    @Benchmark
    public void appendCheckBeforeCopy() {
        checkReset();
        if (pos == chars.length) {
            expand();
        }
        chars[pos++] = simulatedData;
    }

    @Benchmark
    public void appendCatchExceptionAfter() {
        checkReset();
        try {
            chars[pos++] = simulatedData;
        } catch (IndexOutOfBoundsException e) {
            expand();
            chars[pos - 1] = simulatedData;
        }
    }

    private void checkReset() {
        if (pos == simulatedDataSize) {
            pos = 0;
            chars = new char[charsInitSize];
        }
    }

    private void expand() {
        char[] old = chars;
        // double it
        int len = old.length << 1;
        if (len < 0) {
            throw new IllegalStateException();
        }
        chars = new char[len];
        System.arraycopy(old, 0, chars, 0, old.length);
    }
}
