package io.netty.handler.codec.http.websocketx.extensions;


/**
 * Handshakes a client extension with the server.
 */
public interface WebSocketClientExtensionHandshaker {

    /**
     * Return extension configuration to submit to the server.
     *
     * @return the desired extension configuration.
     */
    WebSocketExtensionData newRequestData();

    /**
     * Handshake based on server response. It should always succeed because server response
     * should be a request acknowledge.
     *
     * @param extensionData the extension configuration sent by the server.
     * @return an initialized extension if handshake phase succeed or null if failed.
     */
    WebSocketClientExtension handshakeExtension(WebSocketExtensionData extensionData);

}
