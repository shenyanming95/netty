package io.netty.handler.codec;

import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Threads(16)
public class AdvancedCodecOutputListBenchmark extends AbstractMicrobenchmark {

    private static final Object ELEMENT = new Object();

    @Param({"1", "4"})
    public int elements;

    private static boolean benchmark(int elements, CodecOutputList list1, CodecOutputList list2, CodecOutputList list3, CodecOutputList list4) {
        return (benchmark(elements, list1) == benchmark(elements, list2)) == (benchmark(elements, list3) == benchmark(elements, list4));
    }

    private static boolean benchmark(int elements, CodecOutputList list) {
        for (int i = 0; i < elements; ++i) {
            list.add(ELEMENT);
        }
        list.recycle();
        return list.insertSinceRecycled();
    }

    @Benchmark
    public boolean codecOutListAllocRecycle() {
        return benchmark(elements, CodecOutputList.newInstance(), CodecOutputList.newInstance(), CodecOutputList.newInstance(), CodecOutputList.newInstance());
    }
}
