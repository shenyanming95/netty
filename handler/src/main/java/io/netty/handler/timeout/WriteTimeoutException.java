package io.netty.handler.timeout;

import io.netty.util.internal.PlatformDependent;

/**
 * A {@link TimeoutException} raised by {@link WriteTimeoutHandler} when a write operation
 * cannot finish in a certain period of time.
 */
public final class WriteTimeoutException extends TimeoutException {

    public static final WriteTimeoutException INSTANCE = PlatformDependent.javaVersion() >= 7 ? new WriteTimeoutException(true) : new WriteTimeoutException();
    private static final long serialVersionUID = -144786655770296065L;

    private WriteTimeoutException() {
    }

    private WriteTimeoutException(boolean shared) {
        super(shared);
    }
}
