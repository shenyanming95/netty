package io.netty.buffer;

public interface ByteBufAllocatorMetricProvider {

    /**
     * Returns a {@link ByteBufAllocatorMetric} for a {@link ByteBufAllocator}.
     */
    ByteBufAllocatorMetric metric();
}
