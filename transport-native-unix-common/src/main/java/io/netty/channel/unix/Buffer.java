package io.netty.channel.unix;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.UnstableApi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@UnstableApi
public final class Buffer {

    private Buffer() {
    }

    /**
     * Free the direct {@link ByteBuffer}.
     */
    public static void free(ByteBuffer buffer) {
        PlatformDependent.freeDirectBuffer(buffer);
    }

    /**
     * Returns a new {@link ByteBuffer} which has the same {@link ByteOrder} as the native order of the machine.
     */
    public static ByteBuffer allocateDirectWithNativeOrder(int capacity) {
        return ByteBuffer.allocateDirect(capacity).order(PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * Returns the memory address of the given direct {@link ByteBuffer}.
     */
    public static long memoryAddress(ByteBuffer buffer) {
        assert buffer.isDirect();
        if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.directBufferAddress(buffer);
        }
        return memoryAddress0(buffer);
    }

    /**
     * Returns the size of a pointer.
     */
    public static int addressSize() {
        if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.addressSize();
        }
        return addressSize0();
    }

    // If Unsafe can not be used we will need to do JNI calls.
    private static native int addressSize0();

    private static native long memoryAddress0(ByteBuffer buffer);
}
