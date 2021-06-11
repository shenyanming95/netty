package io.netty.handler.codec;

/**
 * An {@link DecoderException} which is thrown when the received frame data could not be decoded by
 * an inbound handler.
 */
public class CorruptedFrameException extends DecoderException {

    private static final long serialVersionUID = 3918052232492988408L;

    /**
     * Creates a new instance.
     */
    public CorruptedFrameException() {
    }

    /**
     * Creates a new instance.
     */
    public CorruptedFrameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance.
     */
    public CorruptedFrameException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     */
    public CorruptedFrameException(Throwable cause) {
        super(cause);
    }
}
