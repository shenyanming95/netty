package io.netty.handler.codec.http;

import io.netty.util.AsciiString;

/**
 * Defines the common schemes used for the HTTP protocol as defined by
 * <a href="https://tools.ietf.org/html/rfc7230">rfc7230</a>.
 */
public final class HttpScheme {

    /**
     * Scheme for non-secure HTTP connection.
     */
    public static final HttpScheme HTTP = new HttpScheme(80, "http");

    /**
     * Scheme for secure HTTP connection.
     */
    public static final HttpScheme HTTPS = new HttpScheme(443, "https");

    private final int port;
    private final AsciiString name;

    private HttpScheme(int port, String name) {
        this.port = port;
        this.name = AsciiString.cached(name);
    }

    public AsciiString name() {
        return name;
    }

    public int port() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HttpScheme)) {
            return false;
        }
        HttpScheme other = (HttpScheme) o;
        return other.port() == port && other.name().equals(name);
    }

    @Override
    public int hashCode() {
        return port * 31 + name.hashCode();
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
