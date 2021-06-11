package io.netty.handler.codec.http;

/**
 * A user event designed to communicate that a expectation has failed and there should be no expectation that a
 * body will follow.
 */
public final class HttpExpectationFailedEvent {
    public static final HttpExpectationFailedEvent INSTANCE = new HttpExpectationFailedEvent();

    private HttpExpectationFailedEvent() {
    }
}
