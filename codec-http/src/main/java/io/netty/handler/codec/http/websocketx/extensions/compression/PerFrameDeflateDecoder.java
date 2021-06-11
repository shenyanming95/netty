package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilter;

/**
 * Per-frame implementation of deflate decompressor.
 */
class PerFrameDeflateDecoder extends DeflateDecoder {

    /**
     * Constructor
     *
     * @param noContext true to disable context takeover.
     */
    PerFrameDeflateDecoder(boolean noContext) {
        super(noContext, WebSocketExtensionFilter.NEVER_SKIP);
    }

    /**
     * Constructor
     *
     * @param noContext              true to disable context takeover.
     * @param extensionDecoderFilter extension decoder filter for per frame deflate decoder.
     */
    PerFrameDeflateDecoder(boolean noContext, WebSocketExtensionFilter extensionDecoderFilter) {
        super(noContext, extensionDecoderFilter);
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        if (!super.acceptInboundMessage(msg)) {
            return false;
        }

        WebSocketFrame wsFrame = (WebSocketFrame) msg;
        if (extensionDecoderFilter().mustSkip(wsFrame)) {
            return false;
        }

        return (msg instanceof TextWebSocketFrame || msg instanceof BinaryWebSocketFrame || msg instanceof ContinuationWebSocketFrame) && (wsFrame.rsv() & WebSocketExtension.RSV1) > 0;
    }

    @Override
    protected int newRsv(WebSocketFrame msg) {
        return msg.rsv() ^ WebSocketExtension.RSV1;
    }

    @Override
    protected boolean appendFrameTail(WebSocketFrame msg) {
        return true;
    }

}
