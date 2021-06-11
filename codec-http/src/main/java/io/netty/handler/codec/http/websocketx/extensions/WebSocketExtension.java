package io.netty.handler.codec.http.websocketx.extensions;

/**
 * Created once the handshake phase is done.
 */
public interface WebSocketExtension {

    int RSV1 = 0x04;
    int RSV2 = 0x02;
    int RSV3 = 0x01;

    /**
     * @return the reserved bit value to ensure that no other extension should interfere.
     */
    int rsv();

    /**
     * @return create the extension encoder.
     */
    WebSocketExtensionEncoder newExtensionEncoder();

    /**
     * @return create the extension decoder.
     */
    WebSocketExtensionDecoder newExtensionDecoder();

}
