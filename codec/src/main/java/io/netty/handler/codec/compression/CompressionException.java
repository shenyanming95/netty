package io.netty.handler.codec.compression;

import io.netty.handler.codec.EncoderException;

/**
 * An {@link EncoderException} that is raised when compression failed.
 */
public class CompressionException extends EncoderException {

    private static final long serialVersionUID = 5603413481274811897L;

    /**
     * Creates a new instance.
     */
    public CompressionException() {
    }

    /**
     * Creates a new instance.
     */
    public CompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance.
     */
    public CompressionException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     */
    public CompressionException(Throwable cause) {
        super(cause);
    }
}
