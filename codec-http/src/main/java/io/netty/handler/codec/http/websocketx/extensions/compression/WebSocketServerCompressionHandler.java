package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandler;

/**
 * Extends <tt>io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerExtensionHandler</tt>
 * to handle the most common WebSocket Compression Extensions.
 * <p>
 * See <tt>io.netty.example.http.websocketx.html5.WebSocketServer</tt> for usage.
 */
public class WebSocketServerCompressionHandler extends WebSocketServerExtensionHandler {

    /**
     * Constructor with default configuration.
     */
    public WebSocketServerCompressionHandler() {
        super(new PerMessageDeflateServerExtensionHandshaker(), new DeflateFrameServerExtensionHandshaker());
    }

}
