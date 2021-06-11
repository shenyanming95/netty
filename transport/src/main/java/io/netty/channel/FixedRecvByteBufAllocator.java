package io.netty.channel;

import static io.netty.util.internal.ObjectUtil.checkPositive;

/**
 * The {@link RecvByteBufAllocator} that always yields the same buffer
 * size prediction.  This predictor ignores the feed back from the I/O thread.
 */
public class FixedRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {

    private final int bufferSize;

    /**
     * Creates a new predictor that always returns the same prediction of
     * the specified buffer size.
     */
    public FixedRecvByteBufAllocator(int bufferSize) {
        checkPositive(bufferSize, "bufferSize");
        this.bufferSize = bufferSize;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Handle newHandle() {
        return new HandleImpl(bufferSize);
    }

    @Override
    public FixedRecvByteBufAllocator respectMaybeMoreData(boolean respectMaybeMoreData) {
        super.respectMaybeMoreData(respectMaybeMoreData);
        return this;
    }

    private final class HandleImpl extends MaxMessageHandle {
        private final int bufferSize;

        HandleImpl(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        @Override
        public int guess() {
            return bufferSize;
        }
    }
}
