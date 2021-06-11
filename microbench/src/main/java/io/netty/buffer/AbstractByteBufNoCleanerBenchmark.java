package io.netty.buffer;

import io.netty.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.Param;

public abstract class AbstractByteBufNoCleanerBenchmark extends AbstractMicrobenchmark {

    @Param
    public ByteBufType bufferType;

    public enum ByteBufType {
        UNPOOLED_NO_CLEANER {
            @Override
            ByteBuf newBuffer(int initialCapacity) {
                return new UnpooledUnsafeNoCleanerDirectByteBuf(UnpooledByteBufAllocator.DEFAULT, initialCapacity, Integer.MAX_VALUE);
            }
        }, UNPOOLED {
            @Override
            ByteBuf newBuffer(int initialCapacity) {
                return new UnpooledUnsafeDirectByteBuf(UnpooledByteBufAllocator.DEFAULT, initialCapacity, Integer.MAX_VALUE);
            }
        };

        abstract ByteBuf newBuffer(int initialCapacity);
    }
}
