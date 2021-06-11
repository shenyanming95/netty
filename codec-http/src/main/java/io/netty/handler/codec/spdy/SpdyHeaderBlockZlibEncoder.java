package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SuppressJava6Requirement;

import java.util.zip.Deflater;

import static io.netty.handler.codec.spdy.SpdyCodecUtil.SPDY_DICT;

class SpdyHeaderBlockZlibEncoder extends SpdyHeaderBlockRawEncoder {

    private final Deflater compressor;

    private boolean finished;

    SpdyHeaderBlockZlibEncoder(SpdyVersion spdyVersion, int compressionLevel) {
        super(spdyVersion);
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        compressor = new Deflater(compressionLevel);
        compressor.setDictionary(SPDY_DICT);
    }

    private int setInput(ByteBuf decompressed) {
        int len = decompressed.readableBytes();

        if (decompressed.hasArray()) {
            compressor.setInput(decompressed.array(), decompressed.arrayOffset() + decompressed.readerIndex(), len);
        } else {
            byte[] in = new byte[len];
            decompressed.getBytes(decompressed.readerIndex(), in);
            compressor.setInput(in, 0, in.length);
        }

        return len;
    }

    private ByteBuf encode(ByteBufAllocator alloc, int len) {
        ByteBuf compressed = alloc.heapBuffer(len);
        boolean release = true;
        try {
            while (compressInto(compressed)) {
                // Although unlikely, it's possible that the compressed size is larger than the decompressed size
                compressed.ensureWritable(compressed.capacity() << 1);
            }
            release = false;
            return compressed;
        } finally {
            if (release) {
                compressed.release();
            }
        }
    }

    @SuppressJava6Requirement(reason = "Guarded by java version check")
    private boolean compressInto(ByteBuf compressed) {
        byte[] out = compressed.array();
        int off = compressed.arrayOffset() + compressed.writerIndex();
        int toWrite = compressed.writableBytes();
        final int numBytes;
        if (PlatformDependent.javaVersion() >= 7) {
            numBytes = compressor.deflate(out, off, toWrite, Deflater.SYNC_FLUSH);
        } else {
            numBytes = compressor.deflate(out, off, toWrite);
        }
        compressed.writerIndex(compressed.writerIndex() + numBytes);
        return numBytes == toWrite;
    }

    @Override
    public ByteBuf encode(ByteBufAllocator alloc, SpdyHeadersFrame frame) throws Exception {
        if (frame == null) {
            throw new IllegalArgumentException("frame");
        }

        if (finished) {
            return Unpooled.EMPTY_BUFFER;
        }

        ByteBuf decompressed = super.encode(alloc, frame);
        try {
            if (!decompressed.isReadable()) {
                return Unpooled.EMPTY_BUFFER;
            }

            int len = setInput(decompressed);
            return encode(alloc, len);
        } finally {
            decompressed.release();
        }
    }

    @Override
    public void end() {
        if (finished) {
            return;
        }
        finished = true;
        compressor.end();
        super.end();
    }
}
