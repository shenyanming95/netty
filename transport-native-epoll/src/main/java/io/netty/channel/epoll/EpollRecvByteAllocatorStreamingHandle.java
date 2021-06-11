package io.netty.channel.epoll;

import io.netty.channel.RecvByteBufAllocator;

final class EpollRecvByteAllocatorStreamingHandle extends EpollRecvByteAllocatorHandle {
    EpollRecvByteAllocatorStreamingHandle(RecvByteBufAllocator.ExtendedHandle handle) {
        super(handle);
    }

    @Override
    boolean maybeMoreDataToRead() {
        /**
         * For stream oriented descriptors we can assume we are done reading if the last read attempt didn't produce
         * a full buffer (see Q9 in <a href="http://man7.org/linux/man-pages/man7/epoll.7.html">epoll man</a>).
         *
         * If EPOLLRDHUP has been received we must read until we get a read error.
         */
        return lastBytesRead() == attemptedBytesRead() || isReceivedRdHup();
    }
}
