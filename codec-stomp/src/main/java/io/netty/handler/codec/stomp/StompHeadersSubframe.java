package io.netty.handler.codec.stomp;

/**
 * An interface that defines a {@link StompFrame}'s command and headers.
 *
 * @see StompCommand
 * @see StompHeaders
 */
public interface StompHeadersSubframe extends StompSubframe {
    /**
     * Returns command of this frame.
     */
    StompCommand command();

    /**
     * Returns headers of this frame.
     */
    StompHeaders headers();
}
