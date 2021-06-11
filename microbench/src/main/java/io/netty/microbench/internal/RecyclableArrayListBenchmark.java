package io.netty.microbench.internal;

import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.internal.RecyclableArrayList;
import org.openjdk.jmh.annotations.*;

/**
 * This class benchmarks different allocators with different allocation sizes.
 */
@State(Scope.Benchmark)
@Threads(4)
@Measurement(iterations = 10, batchSize = 100)
public class RecyclableArrayListBenchmark extends AbstractMicrobenchmark {

    @Param({"00000", "00256", "01024", "04096", "16384", "65536"})
    public int size;

    @Benchmark
    public boolean recycleSameThread() {
        RecyclableArrayList list = RecyclableArrayList.newInstance(size);
        return list.recycle();
    }
}
