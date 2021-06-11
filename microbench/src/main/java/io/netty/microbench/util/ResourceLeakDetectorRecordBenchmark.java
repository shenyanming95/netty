package io.netty.microbench.util;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakHint;
import io.netty.util.ResourceLeakTracker;
import org.openjdk.jmh.annotations.*;

public class ResourceLeakDetectorRecordBenchmark extends AbstractMicrobenchmark {
    private static final Object TRACKED = new Object();
    private static final ResourceLeakHint HINT = new ResourceLeakHint() {
        @Override
        public String toHintString() {
            return "BenchmarkHint";
        }
    };
    ResourceLeakDetector<Object> detector = new ResourceLeakDetector<Object>(Object.class, 1, Integer.MAX_VALUE) {
        @Override
        protected void reportTracedLeak(String resourceType, String records) {
            // noop
        }

        @Override
        protected void reportUntracedLeak(String resourceType) {
            // noop
        }

        @Override
        protected void reportInstancesLeak(String resourceType) {
            // noop
        }
    };
    @Param({"8", "16"})
    private int recordTimes;
    private ResourceLeakDetector.Level level;

    @Setup(Level.Trial)
    public void setup() {
        level = ResourceLeakDetector.getLevel();
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
    }

    @TearDown(Level.Trial)
    public void teardown() {
        ResourceLeakDetector.setLevel(level);
    }

    @Benchmark
    public boolean record() {
        ResourceLeakTracker<Object> tracker = detector.track(TRACKED);
        for (int i = 0; i < recordTimes; i++) {
            tracker.record();
        }
        return tracker.close(TRACKED);
    }

    @Benchmark
    public boolean recordWithHint() {
        ResourceLeakTracker<Object> tracker = detector.track(TRACKED);
        for (int i = 0; i < recordTimes; i++) {
            tracker.record(HINT);
        }
        return tracker.close(TRACKED);
    }
}
