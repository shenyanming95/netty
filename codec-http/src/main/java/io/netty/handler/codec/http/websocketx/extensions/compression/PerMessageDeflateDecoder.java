package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilter;

import java.util.List;

/**
 * Per-message implementation of deflate decompressor.
 */
class PerMessageDeflateDecoder extends DeflateDecoder {

    private boolean compressing;

    /**
     * Constructor
     *
     * @param noContext true to disable context takeover.
     */
    PerMessageDeflateDecoder(boolean noContext) {
        super(noContext, WebSocketExtensionFilter.NEVER_SKIP);
    }

    /**
     * Constructor
     *
     * @param noContext              true to disable context takeover.
     * @param extensionDecoderFilter extension decoder for per message deflate decoder.
     */
    PerMessageDeflateDecoder(boolean noContext, WebSocketExtensionFilter extensionDecoderFilter) {
        super(noContext, extensionDecoderFilter);
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        if (!super.acceptInboundMessage(msg)) {
            return false;
        }

        WebSocketFrame wsFrame = (WebSocketFrame) msg;
        if (extensionDecoderFilter().mustSkip(wsFrame)) {
            if (compressing) {
                throw new IllegalStateException("Cannot skip per message deflate decoder, compression in progress");
            }
            return false;
        }

        return ((wsFrame instanceof TextWebSocketFrame || wsFrame instanceof BinaryWebSocketFrame) && (wsFrame.rsv() & WebSocketExtension.RSV1) > 0) || (wsFrame instanceof ContinuationWebSocketFrame && compressing);
    }

    @Override
    protected int newRsv(WebSocketFrame msg) {
        return (msg.rsv() & WebSocketExtension.RSV1) > 0 ? msg.rsv() ^ WebSocketExtension.RSV1 : msg.rsv();
    }

    @Override
    protected boolean appendFrameTail(WebSocketFrame msg) {
        return msg.isFinalFragment();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        super.decode(ctx, msg, out);

        if (msg.isFinalFragment()) {
            compressing = false;
        } else if (msg instanceof TextWebSocketFrame || msg instanceof BinaryWebSocketFrame) {
            compressing = true;
        }
    }

}
