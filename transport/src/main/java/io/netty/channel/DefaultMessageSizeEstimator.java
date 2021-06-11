package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

/**
 * Default {@link MessageSizeEstimator} implementation which supports the estimation of the size of
 * {@link ByteBuf}, {@link ByteBufHolder} and {@link FileRegion}.
 */
public final class DefaultMessageSizeEstimator implements MessageSizeEstimator {

    /**
     * Return the default implementation which returns {@code 8} for unknown messages.
     */
    public static final MessageSizeEstimator DEFAULT = new DefaultMessageSizeEstimator(8);
    private final Handle handle;

    /**
     * Create a new instance
     *
     * @param unknownSize The size which is returned for unknown messages.
     */
    public DefaultMessageSizeEstimator(int unknownSize) {
        checkPositiveOrZero(unknownSize, "unknownSize");
        handle = new HandleImpl(unknownSize);
    }

    @Override
    public Handle newHandle() {
        return handle;
    }

    private static final class HandleImpl implements Handle {
        private final int unknownSize;

        private HandleImpl(int unknownSize) {
            this.unknownSize = unknownSize;
        }

        @Override
        public int size(Object msg) {
            if (msg instanceof ByteBuf) {
                return ((ByteBuf) msg).readableBytes();
            }
            if (msg instanceof ByteBufHolder) {
                return ((ByteBufHolder) msg).content().readableBytes();
            }
            if (msg instanceof FileRegion) {
                return 0;
            }
            return unknownSize;
        }
    }
}
