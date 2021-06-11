package io.netty.util.internal;

import java.nio.ByteBuffer;

/**
 * Allows to free direct {@link ByteBuffer}s.
 */
interface Cleaner {

    /**
     * Free a direct {@link ByteBuffer} if possible
     */
    void freeDirectBuffer(ByteBuffer buffer);
}
