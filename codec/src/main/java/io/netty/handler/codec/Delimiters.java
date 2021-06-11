package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * A set of commonly used delimiters for {@link DelimiterBasedFrameDecoder}.
 */
public final class Delimiters {

    private Delimiters() {
        // Unused
    }

    /**
     * Returns a {@code NUL (0x00)} delimiter, which could be used for
     * Flash XML socket or any similar protocols.
     */
    public static ByteBuf[] nulDelimiter() {
        return new ByteBuf[]{Unpooled.wrappedBuffer(new byte[]{0})};
    }

    /**
     * Returns {@code CR ('\r')} and {@code LF ('\n')} delimiters, which could
     * be used for text-based line protocols.
     */
    public static ByteBuf[] lineDelimiter() {
        return new ByteBuf[]{Unpooled.wrappedBuffer(new byte[]{'\r', '\n'}), Unpooled.wrappedBuffer(new byte[]{'\n'}),};
    }
}
