package io.netty.handler.codec.http.websocketx.extensions;


/**
 * Handshakes a client extension based on this server capabilities.
 */
public interface WebSocketServerExtensionHandshaker {

    /**
     * Handshake based on client request. It must failed with <tt>null</tt> if server cannot handle it.
     *
     * @param extensionData the extension configuration sent by the client.
     * @return an initialized extension if handshake phase succeed or null if failed.
     */
    WebSocketServerExtension handshakeExtension(WebSocketExtensionData extensionData);

}
