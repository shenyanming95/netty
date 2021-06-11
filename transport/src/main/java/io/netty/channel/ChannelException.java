package io.netty.channel;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SuppressJava6Requirement;
import io.netty.util.internal.UnstableApi;

/**
 * A {@link RuntimeException} which is thrown when an I/O operation fails.
 */
public class ChannelException extends RuntimeException {

    private static final long serialVersionUID = 2908618315971075004L;

    /**
     * Creates a new exception.
     */
    public ChannelException() {
    }

    /**
     * Creates a new exception.
     */
    public ChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception.
     */
    public ChannelException(String message) {
        super(message);
    }

    /**
     * Creates a new exception.
     */
    public ChannelException(Throwable cause) {
        super(cause);
    }

    @UnstableApi
    @SuppressJava6Requirement(reason = "uses Java 7+ RuntimeException.<init>(String, Throwable, boolean, boolean)" + " but is guarded by version checks")
    protected ChannelException(String message, Throwable cause, boolean shared) {
        super(message, cause, false, true);
        assert shared;
    }

    static ChannelException newStatic(String message, Throwable cause) {
        if (PlatformDependent.javaVersion() >= 7) {
            return new ChannelException(message, cause, true);
        }
        return new ChannelException(message, cause);
    }
}
