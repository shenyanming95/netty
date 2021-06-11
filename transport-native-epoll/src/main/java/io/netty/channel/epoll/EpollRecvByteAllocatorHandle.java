package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator.DelegatingHandle;
import io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import io.netty.channel.unix.PreferredDirectByteBufAllocator;
import io.netty.util.UncheckedBooleanSupplier;

class EpollRecvByteAllocatorHandle extends DelegatingHandle implements ExtendedHandle {
    private final PreferredDirectByteBufAllocator preferredDirectByteBufAllocator = new PreferredDirectByteBufAllocator();
    private boolean isEdgeTriggered;
    private final UncheckedBooleanSupplier defaultMaybeMoreDataSupplier = new UncheckedBooleanSupplier() {
        @Override
        public boolean get() {
            return maybeMoreDataToRead();
        }
    };
    private boolean receivedRdHup;

    EpollRecvByteAllocatorHandle(ExtendedHandle handle) {
        super(handle);
    }

    final void receivedRdHup() {
        receivedRdHup = true;
    }

    final boolean isReceivedRdHup() {
        return receivedRdHup;
    }

    boolean maybeMoreDataToRead() {
        /**
         * EPOLL ET requires that we read until we get an EAGAIN
         * (see Q9 in <a href="http://man7.org/linux/man-pages/man7/epoll.7.html">epoll man</a>). However in order to
         * respect auto read we supporting reading to stop if auto read is off. It is expected that the
         * {@link #EpollSocketChannel} implementations will track if we are in edgeTriggered mode and all data was not
         * read, and will force a EPOLLIN ready event.
         *
         * It is assumed RDHUP is handled externally by checking {@link #isReceivedRdHup()}.
         */
        return (isEdgeTriggered && lastBytesRead() > 0) || (!isEdgeTriggered && lastBytesRead() == attemptedBytesRead());
    }

    final void edgeTriggered(boolean edgeTriggered) {
        isEdgeTriggered = edgeTriggered;
    }

    final boolean isEdgeTriggered() {
        return isEdgeTriggered;
    }

    @Override
    public final ByteBuf allocate(ByteBufAllocator alloc) {
        // We need to ensure we always allocate a direct ByteBuf as we can only use a direct buffer to read via JNI.
        preferredDirectByteBufAllocator.updateAllocator(alloc);
        return delegate().allocate(preferredDirectByteBufAllocator);
    }

    @Override
    public final boolean continueReading(UncheckedBooleanSupplier maybeMoreDataSupplier) {
        return ((ExtendedHandle) delegate()).continueReading(maybeMoreDataSupplier);
    }

    @Override
    public final boolean continueReading() {
        // We must override the supplier which determines if there maybe more data to read.
        return continueReading(defaultMaybeMoreDataSupplier);
    }
}
