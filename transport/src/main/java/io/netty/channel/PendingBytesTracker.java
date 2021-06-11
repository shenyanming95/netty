package io.netty.channel;

import io.netty.util.internal.ObjectUtil;

abstract class PendingBytesTracker implements MessageSizeEstimator.Handle {
    private final MessageSizeEstimator.Handle estimatorHandle;

    private PendingBytesTracker(MessageSizeEstimator.Handle estimatorHandle) {
        this.estimatorHandle = ObjectUtil.checkNotNull(estimatorHandle, "estimatorHandle");
    }

    static PendingBytesTracker newTracker(Channel channel) {
        if (channel.pipeline() instanceof DefaultChannelPipeline) {
            return new DefaultChannelPipelinePendingBytesTracker((DefaultChannelPipeline) channel.pipeline());
        } else {
            ChannelOutboundBuffer buffer = channel.unsafe().outboundBuffer();
            MessageSizeEstimator.Handle handle = channel.config().getMessageSizeEstimator().newHandle();
            // We need to guard against null as channel.unsafe().outboundBuffer() may returned null
            // if the channel was already closed when constructing the PendingBytesTracker.
            // See https://github.com/netty/netty/issues/3967
            return buffer == null ? new NoopPendingBytesTracker(handle) : new ChannelOutboundBufferPendingBytesTracker(buffer, handle);
        }
    }

    @Override
    public final int size(Object msg) {
        return estimatorHandle.size(msg);
    }

    public abstract void incrementPendingOutboundBytes(long bytes);

    public abstract void decrementPendingOutboundBytes(long bytes);

    private static final class DefaultChannelPipelinePendingBytesTracker extends PendingBytesTracker {
        private final DefaultChannelPipeline pipeline;

        DefaultChannelPipelinePendingBytesTracker(DefaultChannelPipeline pipeline) {
            super(pipeline.estimatorHandle());
            this.pipeline = pipeline;
        }

        @Override
        public void incrementPendingOutboundBytes(long bytes) {
            pipeline.incrementPendingOutboundBytes(bytes);
        }

        @Override
        public void decrementPendingOutboundBytes(long bytes) {
            pipeline.decrementPendingOutboundBytes(bytes);
        }
    }

    private static final class ChannelOutboundBufferPendingBytesTracker extends PendingBytesTracker {
        private final ChannelOutboundBuffer buffer;

        ChannelOutboundBufferPendingBytesTracker(ChannelOutboundBuffer buffer, MessageSizeEstimator.Handle estimatorHandle) {
            super(estimatorHandle);
            this.buffer = buffer;
        }

        @Override
        public void incrementPendingOutboundBytes(long bytes) {
            buffer.incrementPendingOutboundBytes(bytes);
        }

        @Override
        public void decrementPendingOutboundBytes(long bytes) {
            buffer.decrementPendingOutboundBytes(bytes);
        }
    }

    private static final class NoopPendingBytesTracker extends PendingBytesTracker {

        NoopPendingBytesTracker(MessageSizeEstimator.Handle estimatorHandle) {
            super(estimatorHandle);
        }

        @Override
        public void incrementPendingOutboundBytes(long bytes) {
            // Noop
        }

        @Override
        public void decrementPendingOutboundBytes(long bytes) {
            // Noop
        }
    }
}
