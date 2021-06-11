package io.netty.channel;

/**
 * Special {@link ChannelException} which will be thrown by {@link EventLoop} and {@link EventLoopGroup}
 * implementations when an error occurs.
 */
public class EventLoopException extends ChannelException {

    private static final long serialVersionUID = -8969100344583703616L;

    public EventLoopException() {
    }

    public EventLoopException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventLoopException(String message) {
        super(message);
    }

    public EventLoopException(Throwable cause) {
        super(cause);
    }

}
