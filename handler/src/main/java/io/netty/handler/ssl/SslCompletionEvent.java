package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;

public abstract class SslCompletionEvent {

    private final Throwable cause;

    SslCompletionEvent() {
        cause = null;
    }

    SslCompletionEvent(Throwable cause) {
        this.cause = ObjectUtil.checkNotNull(cause, "cause");
    }

    /**
     * Return {@code true} if the completion was successful
     */
    public final boolean isSuccess() {
        return cause == null;
    }

    /**
     * Return the {@link Throwable} if {@link #isSuccess()} returns {@code false}
     * and so the completion failed.
     */
    public final Throwable cause() {
        return cause;
    }

    @Override
    public String toString() {
        final Throwable cause = cause();
        return cause == null ? getClass().getSimpleName() + "(SUCCESS)" : getClass().getSimpleName() + '(' + cause + ')';
    }
}
