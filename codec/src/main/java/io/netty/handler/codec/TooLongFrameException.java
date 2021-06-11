package io.netty.handler.codec;

/**
 * An {@link DecoderException} which is thrown when the length of the frame
 * decoded is greater than the allowed maximum.
 */
public class TooLongFrameException extends DecoderException {

    private static final long serialVersionUID = -1995801950698951640L;

    /**
     * Creates a new instance.
     */
    public TooLongFrameException() {
    }

    /**
     * Creates a new instance.
     */
    public TooLongFrameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance.
     */
    public TooLongFrameException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     */
    public TooLongFrameException(Throwable cause) {
        super(cause);
    }
}
