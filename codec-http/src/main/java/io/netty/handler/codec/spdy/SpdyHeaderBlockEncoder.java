package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.PlatformDependent;

abstract class SpdyHeaderBlockEncoder {

    static SpdyHeaderBlockEncoder newInstance(SpdyVersion version, int compressionLevel, int windowBits, int memLevel) {

        if (PlatformDependent.javaVersion() >= 7) {
            return new SpdyHeaderBlockZlibEncoder(version, compressionLevel);
        } else {
            return new SpdyHeaderBlockJZlibEncoder(version, compressionLevel, windowBits, memLevel);
        }
    }

    abstract ByteBuf encode(ByteBufAllocator alloc, SpdyHeadersFrame frame) throws Exception;

    abstract void end();
}
