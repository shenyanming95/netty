package io.netty.channel.unix;

import io.netty.buffer.ByteBuf;

/**
 * Different modes of reading from a {@link DomainSocketChannel}.
 */
public enum DomainSocketReadMode {

    /**
     * Read {@link ByteBuf}s from the {@link DomainSocketChannel}.
     */
    BYTES,

    /**
     * Read {@link FileDescriptor}s from the {@link DomainSocketChannel}.
     */
    FILE_DESCRIPTORS
}
