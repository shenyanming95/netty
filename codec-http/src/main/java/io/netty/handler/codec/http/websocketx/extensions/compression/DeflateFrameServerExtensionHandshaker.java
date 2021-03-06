package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.*;

import java.util.Collections;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * <a href="https://tools.ietf.org/id/draft-tyoshino-hybi-websocket-perframe-deflate-06.txt">perframe-deflate</a>
 * handshake implementation.
 */
public final class DeflateFrameServerExtensionHandshaker implements WebSocketServerExtensionHandshaker {

    static final String X_WEBKIT_DEFLATE_FRAME_EXTENSION = "x-webkit-deflate-frame";
    static final String DEFLATE_FRAME_EXTENSION = "deflate-frame";

    private final int compressionLevel;
    private final WebSocketExtensionFilterProvider extensionFilterProvider;

    /**
     * Constructor with default configuration.
     */
    public DeflateFrameServerExtensionHandshaker() {
        this(6);
    }

    /**
     * Constructor with custom configuration.
     *
     * @param compressionLevel Compression level between 0 and 9 (default is 6).
     */
    public DeflateFrameServerExtensionHandshaker(int compressionLevel) {
        this(compressionLevel, WebSocketExtensionFilterProvider.DEFAULT);
    }

    /**
     * Constructor with custom configuration.
     *
     * @param compressionLevel        Compression level between 0 and 9 (default is 6).
     * @param extensionFilterProvider provides server extension filters for per frame deflate encoder and decoder.
     */
    public DeflateFrameServerExtensionHandshaker(int compressionLevel, WebSocketExtensionFilterProvider extensionFilterProvider) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        this.compressionLevel = compressionLevel;
        this.extensionFilterProvider = checkNotNull(extensionFilterProvider, "extensionFilterProvider");
    }

    @Override
    public WebSocketServerExtension handshakeExtension(WebSocketExtensionData extensionData) {
        if (!X_WEBKIT_DEFLATE_FRAME_EXTENSION.equals(extensionData.name()) && !DEFLATE_FRAME_EXTENSION.equals(extensionData.name())) {
            return null;
        }

        if (extensionData.parameters().isEmpty()) {
            return new DeflateFrameServerExtension(compressionLevel, extensionData.name(), extensionFilterProvider);
        } else {
            return null;
        }
    }

    private static class DeflateFrameServerExtension implements WebSocketServerExtension {

        private final String extensionName;
        private final int compressionLevel;
        private final WebSocketExtensionFilterProvider extensionFilterProvider;

        DeflateFrameServerExtension(int compressionLevel, String extensionName, WebSocketExtensionFilterProvider extensionFilterProvider) {
            this.extensionName = extensionName;
            this.compressionLevel = compressionLevel;
            this.extensionFilterProvider = extensionFilterProvider;
        }

        @Override
        public int rsv() {
            return RSV1;
        }

        @Override
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new PerFrameDeflateEncoder(compressionLevel, 15, false, extensionFilterProvider.encoderFilter());
        }

        @Override
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new PerFrameDeflateDecoder(false, extensionFilterProvider.decoderFilter());
        }

        @Override
        public WebSocketExtensionData newReponseData() {
            return new WebSocketExtensionData(extensionName, Collections.<String, String>emptyMap());
        }
    }

}
