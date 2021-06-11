package io.netty.handler.codec.http;

/**
 * An HTTP response.
 *
 * <h3>Accessing Cookies</h3>
 * <p>
 * Unlike the Servlet API, {@link io.netty.handler.codec.http.cookie.Cookie} support is provided
 * separately via {@link io.netty.handler.codec.http.cookie.ServerCookieDecoder},
 * {@link io.netty.handler.codec.http.cookie.ClientCookieDecoder},
 * {@link io.netty.handler.codec.http.cookie.ServerCookieEncoder},
 * and {@link io.netty.handler.codec.http.cookie.ClientCookieEncoder}.
 *
 * @see HttpRequest
 * @see io.netty.handler.codec.http.cookie.ServerCookieDecoder
 * @see io.netty.handler.codec.http.cookie.ClientCookieDecoder
 * @see io.netty.handler.codec.http.cookie.ServerCookieEncoder
 * @see io.netty.handler.codec.http.cookie.ClientCookieEncoder
 */
public interface HttpResponse extends HttpMessage {

    /**
     * @deprecated Use {@link #status()} instead.
     */
    @Deprecated
    HttpResponseStatus getStatus();

    /**
     * Set the status of this {@link HttpResponse}.
     */
    HttpResponse setStatus(HttpResponseStatus status);

    /**
     * Returns the status of this {@link HttpResponse}.
     *
     * @return The {@link HttpResponseStatus} of this {@link HttpResponse}
     */
    HttpResponseStatus status();

    @Override
    HttpResponse setProtocolVersion(HttpVersion version);
}
