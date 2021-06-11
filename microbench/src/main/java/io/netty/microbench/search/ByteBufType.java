package io.netty.microbench.search;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public enum ByteBufType {
    HEAP {
        @Override
        ByteBuf newBuffer(byte[] bytes) {
            return Unpooled.wrappedBuffer(bytes, 0, bytes.length);
        }
    }, COMPOSITE {
        @Override
        ByteBuf newBuffer(byte[] bytes) {
            CompositeByteBuf buf = Unpooled.compositeBuffer();
            int length = bytes.length;
            int offset = 0;
            int capacity = length / 8; // 8 buffers per composite

            while (length > 0) {
                buf.addComponent(true, Unpooled.wrappedBuffer(bytes, offset, Math.min(length, capacity)));
                length -= capacity;
                offset += capacity;
            }
            return buf;
        }
    }, DIRECT {
        @Override
        ByteBuf newBuffer(byte[] bytes) {
            return Unpooled.directBuffer(bytes.length).writeBytes(bytes);
        }
    };

    abstract ByteBuf newBuffer(byte[] bytes);
}
