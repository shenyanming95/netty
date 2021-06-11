package io.netty.handler.ssl;

/**
 * Event that is fired once the close_notify was received or if an failure happens before it was received.
 */
public final class SslCloseCompletionEvent extends SslCompletionEvent {

    public static final SslCloseCompletionEvent SUCCESS = new SslCloseCompletionEvent();

    /**
     * Creates a new event that indicates a successful receiving of close_notify.
     */
    private SslCloseCompletionEvent() {
    }

    /**
     * Creates a new event that indicates an close_notify was not received because of an previous error.
     * Use {@link #SUCCESS} to indicate a success.
     */
    public SslCloseCompletionEvent(Throwable cause) {
        super(cause);
    }
}
