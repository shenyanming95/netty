package io.netty.handler.codec.compression;

import io.netty.handler.codec.DecoderException;

/**
 * A {@link DecoderException} that is raised when decompression failed.
 */
public class DecompressionException extends DecoderException {

    private static final long serialVersionUID = 3546272712208105199L;

    /**
     * Creates a new instance.
     */
    public DecompressionException() {
    }

    /**
     * Creates a new instance.
     */
    public DecompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance.
     */
    public DecompressionException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     */
    public DecompressionException(Throwable cause) {
        super(cause);
    }
}
