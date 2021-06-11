package io.netty.microbench.util;

import io.netty.util.ResourceLeakDetector;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class ResourceLeakDetectorBenchmark extends AbstractMicrobenchmark {

    private static final Object DUMMY = new Object();
    private ResourceLeakDetector<Object> detector;

    @Setup
    public void setup() {
        detector = new ResourceLeakDetector<Object>(getClass(), 128, Long.MAX_VALUE);
    }

    @Benchmark
    public Object open() {
        return detector.open(DUMMY);
    }
}
