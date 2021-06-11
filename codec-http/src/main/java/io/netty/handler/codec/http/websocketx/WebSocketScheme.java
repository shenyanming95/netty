package io.netty.handler.codec.http.websocketx;

import io.netty.util.AsciiString;

/**
 * Defines the common schemes used for the WebSocket protocol as defined by
 * <a href="https://tools.ietf.org/html/rfc6455">rfc6455</a>.
 */
public final class WebSocketScheme {
    /**
     * Scheme for non-secure WebSocket connection.
     */
    public static final WebSocketScheme WS = new WebSocketScheme(80, "ws");

    /**
     * Scheme for secure WebSocket connection.
     */
    public static final WebSocketScheme WSS = new WebSocketScheme(443, "wss");

    private final int port;
    private final AsciiString name;

    private WebSocketScheme(int port, String name) {
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
        if (!(o instanceof WebSocketScheme)) {
            return false;
        }
        WebSocketScheme other = (WebSocketScheme) o;
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
