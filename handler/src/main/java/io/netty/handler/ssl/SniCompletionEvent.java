package io.netty.handler.ssl;

import io.netty.util.internal.UnstableApi;

/**
 * Event that is fired once we did a selection of a {@link SslContext} based on the {@code SNI hostname},
 * which may be because it was successful or there was an error.
 */
@UnstableApi
public final class SniCompletionEvent extends SslCompletionEvent {
    private final String hostname;

    SniCompletionEvent(String hostname) {
        this.hostname = hostname;
    }

    SniCompletionEvent(String hostname, Throwable cause) {
        super(cause);
        this.hostname = hostname;
    }

    SniCompletionEvent(Throwable cause) {
        this(null, cause);
    }

    /**
     * Returns the SNI hostname send by the client if we were able to parse it, {@code null} otherwise.
     */
    public String hostname() {
        return hostname;
    }

    @Override
    public String toString() {
        final Throwable cause = cause();
        return cause == null ? getClass().getSimpleName() + "(SUCCESS='" + hostname + "'\")" : getClass().getSimpleName() + '(' + cause + ')';
    }
}
