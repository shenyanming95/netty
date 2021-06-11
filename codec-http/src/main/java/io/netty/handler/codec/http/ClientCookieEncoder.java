package io.netty.handler.codec.http;

import io.netty.handler.codec.http.cookie.ClientCookieDecoder;

/**
 * A <a href="http://tools.ietf.org/html/rfc6265">RFC6265</a> compliant cookie encoder to be used client side,
 * so only name=value pairs are sent.
 * <p>
 * User-Agents are not supposed to interpret cookies, so, if present, {@link Cookie#rawValue()} will be used.
 * Otherwise, {@link Cookie#value()} will be used unquoted.
 * <p>
 * Note that multiple cookies are supposed to be sent at once in a single "Cookie" header.
 *
 * <pre>
 * // Example
 * {@link HttpRequest} req = ...;
 * res.setHeader("Cookie", {@link ClientCookieEncoder}.encode("JSESSIONID", "1234"));
 * </pre>
 *
 * @see ClientCookieDecoder
 */
@Deprecated
public final class ClientCookieEncoder {

    private ClientCookieEncoder() {
        // unused
    }

    /**
     * Encodes the specified cookie into a Cookie header value.
     *
     * @param name  the cookie name
     * @param value the cookie value
     * @return a Rfc6265 style Cookie header value
     */
    @Deprecated
    public static String encode(String name, String value) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(name, value);
    }

    /**
     * Encodes the specified cookie into a Cookie header value.
     *
     * @param cookie the specified cookie
     * @return a Rfc6265 style Cookie header value
     */
    @Deprecated
    public static String encode(Cookie cookie) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookie);
    }

    /**
     * Encodes the specified cookies into a single Cookie header value.
     *
     * @param cookies some cookies
     * @return a Rfc6265 style Cookie header value, null if no cookies are passed.
     */
    @Deprecated
    public static String encode(Cookie... cookies) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookies);
    }

    /**
     * Encodes the specified cookies into a single Cookie header value.
     *
     * @param cookies some cookies
     * @return a Rfc6265 style Cookie header value, null if no cookies are passed.
     */
    @Deprecated
    public static String encode(Iterable<Cookie> cookies) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookies);
    }
}
