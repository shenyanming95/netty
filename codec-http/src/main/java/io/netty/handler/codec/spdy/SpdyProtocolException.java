package io.netty.handler.codec.spdy;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SuppressJava6Requirement;

public class SpdyProtocolException extends Exception {

    private static final long serialVersionUID = 7870000537743847264L;

    /**
     * Creates a new instance.
     */
    public SpdyProtocolException() {
    }

    /**
     * Creates a new instance.
     */
    public SpdyProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance.
     */
    public SpdyProtocolException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     */
    public SpdyProtocolException(Throwable cause) {
        super(cause);
    }

    @SuppressJava6Requirement(reason = "uses Java 7+ Exception.<init>(String, Throwable, boolean, boolean)" + " but is guarded by version checks")
    private SpdyProtocolException(String message, boolean shared) {
        super(message, null, false, true);
        assert shared;
    }

    static SpdyProtocolException newStatic(String message) {
        if (PlatformDependent.javaVersion() >= 7) {
            return new SpdyProtocolException(message, true);
        }
        return new SpdyProtocolException(message);
    }
}
