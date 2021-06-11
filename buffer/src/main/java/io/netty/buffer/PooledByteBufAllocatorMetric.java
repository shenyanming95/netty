package io.netty.buffer;

import io.netty.util.internal.StringUtil;

import java.util.List;

/**
 * Exposed metric for {@link PooledByteBufAllocator}.
 */
@SuppressWarnings("deprecation")
public final class PooledByteBufAllocatorMetric implements ByteBufAllocatorMetric {

    private final PooledByteBufAllocator allocator;

    PooledByteBufAllocatorMetric(PooledByteBufAllocator allocator) {
        this.allocator = allocator;
    }

    /**
     * Return the number of heap arenas.
     */
    public int numHeapArenas() {
        return allocator.numHeapArenas();
    }

    /**
     * Return the number of direct arenas.
     */
    public int numDirectArenas() {
        return allocator.numDirectArenas();
    }

    /**
     * Return a {@link List} of all heap {@link PoolArenaMetric}s that are provided by this pool.
     */
    public List<PoolArenaMetric> heapArenas() {
        return allocator.heapArenas();
    }

    /**
     * Return a {@link List} of all direct {@link PoolArenaMetric}s that are provided by this pool.
     */
    public List<PoolArenaMetric> directArenas() {
        return allocator.directArenas();
    }

    /**
     * Return the number of thread local caches used by this {@link PooledByteBufAllocator}.
     */
    public int numThreadLocalCaches() {
        return allocator.numThreadLocalCaches();
    }

    /**
     * Return the size of the tiny cache.
     *
     * @deprecated Tiny caches have been merged into small caches.
     */
    @Deprecated
    public int tinyCacheSize() {
        return allocator.tinyCacheSize();
    }

    /**
     * Return the size of the small cache.
     */
    public int smallCacheSize() {
        return allocator.smallCacheSize();
    }

    /**
     * Return the size of the normal cache.
     */
    public int normalCacheSize() {
        return allocator.normalCacheSize();
    }

    /**
     * Return the chunk size for an arena.
     */
    public int chunkSize() {
        return allocator.chunkSize();
    }

    @Override
    public long usedHeapMemory() {
        return allocator.usedHeapMemory();
    }

    @Override
    public long usedDirectMemory() {
        return allocator.usedDirectMemory();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(StringUtil.simpleClassName(this)).append("(usedHeapMemory: ").append(usedHeapMemory()).append("; usedDirectMemory: ").append(usedDirectMemory()).append("; numHeapArenas: ").append(numHeapArenas()).append("; numDirectArenas: ").append(numDirectArenas()).append("; smallCacheSize: ").append(smallCacheSize()).append("; normalCacheSize: ").append(normalCacheSize()).append("; numThreadLocalCaches: ").append(numThreadLocalCaches()).append("; chunkSize: ").append(chunkSize()).append(')');
        return sb.toString();
    }
}
