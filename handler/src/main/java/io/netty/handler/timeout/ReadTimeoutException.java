package io.netty.handler.timeout;

import io.netty.util.internal.PlatformDependent;

/**
 * A {@link TimeoutException} raised by {@link ReadTimeoutHandler} when no data
 * was read within a certain period of time.
 */
public final class ReadTimeoutException extends TimeoutException {

    public static final ReadTimeoutException INSTANCE = PlatformDependent.javaVersion() >= 7 ? new ReadTimeoutException(true) : new ReadTimeoutException();
    private static final long serialVersionUID = 169287984113283421L;

    ReadTimeoutException() {
    }

    private ReadTimeoutException(boolean shared) {
        super(shared);
    }
}
