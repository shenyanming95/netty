package io.netty.handler.ssl;


/**
 * Event that is fired once the SSL handshake is complete, which may be because it was successful or there
 * was an error.
 */
public final class SslHandshakeCompletionEvent extends SslCompletionEvent {

    public static final SslHandshakeCompletionEvent SUCCESS = new SslHandshakeCompletionEvent();

    /**
     * Creates a new event that indicates a successful handshake.
     */
    private SslHandshakeCompletionEvent() {
    }

    /**
     * Creates a new event that indicates an unsuccessful handshake.
     * Use {@link #SUCCESS} to indicate a successful handshake.
     */
    public SslHandshakeCompletionEvent(Throwable cause) {
        super(cause);
    }
}
