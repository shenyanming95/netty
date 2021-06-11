package io.netty.handler.codec.http;

import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

import java.util.Collection;
import java.util.List;

/**
 * A <a href="http://tools.ietf.org/html/rfc6265">RFC6265</a> compliant cookie encoder to be used server side,
 * so some fields are sent (Version is typically ignored).
 * <p>
 * As Netty's Cookie merges Expires and MaxAge into one single field, only Max-Age field is sent.
 * <p>
 * Note that multiple cookies must be sent as separate "Set-Cookie" headers.
 *
 * <pre>
 * // Example
 * {@link HttpResponse} res = ...;
 * res.setHeader("Set-Cookie", {@link ServerCookieEncoder}.encode("JSESSIONID", "1234"));
 * </pre>
 *
 * @see ServerCookieDecoder
 * @deprecated Use {@link io.netty.handler.codec.http.cookie.ServerCookieEncoder} instead
 */
@Deprecated
public final class ServerCookieEncoder {

    private ServerCookieEncoder() {
        // Unused
    }

    /**
     * Encodes the specified cookie name-value pair into a Set-Cookie header value.
     *
     * @param name  the cookie name
     * @param value the cookie value
     * @return a single Set-Cookie header value
     */
    @Deprecated
    public static String encode(String name, String value) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(name, value);
    }

    /**
     * Encodes the specified cookie into a Set-Cookie header value.
     *
     * @param cookie the cookie
     * @return a single Set-Cookie header value
     */
    @Deprecated
    public static String encode(Cookie cookie) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookie);
    }

    /**
     * Batch encodes cookies into Set-Cookie header values.
     *
     * @param cookies a bunch of cookies
     * @return the corresponding bunch of Set-Cookie headers
     */
    @Deprecated
    public static List<String> encode(Cookie... cookies) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookies);
    }

    /**
     * Batch encodes cookies into Set-Cookie header values.
     *
     * @param cookies a bunch of cookies
     * @return the corresponding bunch of Set-Cookie headers
     */
    @Deprecated
    public static List<String> encode(Collection<Cookie> cookies) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookies);
    }

    /**
     * Batch encodes cookies into Set-Cookie header values.
     *
     * @param cookies a bunch of cookies
     * @return the corresponding bunch of Set-Cookie headers
     */
    @Deprecated
    public static List<String> encode(Iterable<Cookie> cookies) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookies);
    }
}
