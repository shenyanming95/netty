package io.netty.channel;

/**
 * A {@link ChannelException} which is thrown when a {@link ChannelPipeline}
 * failed to execute an operation.
 */
public class ChannelPipelineException extends ChannelException {

    private static final long serialVersionUID = 3379174210419885980L;

    /**
     * Creates a new instance.
     */
    public ChannelPipelineException() {
    }

    /**
     * Creates a new instance.
     */
    public ChannelPipelineException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance.
     */
    public ChannelPipelineException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     */
    public ChannelPipelineException(Throwable cause) {
        super(cause);
    }
}
