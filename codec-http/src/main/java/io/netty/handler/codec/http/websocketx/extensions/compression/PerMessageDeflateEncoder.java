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
 * Per-message implementation of deflate compressor.
 */
class PerMessageDeflateEncoder extends DeflateEncoder {

    private boolean compressing;

    /**
     * Constructor
     *
     * @param compressionLevel compression level of the compressor.
     * @param windowSize       maximum size of the window compressor buffer.
     * @param noContext        true to disable context takeover.
     */
    PerMessageDeflateEncoder(int compressionLevel, int windowSize, boolean noContext) {
        super(compressionLevel, windowSize, noContext, WebSocketExtensionFilter.NEVER_SKIP);
    }

    /**
     * Constructor
     *
     * @param compressionLevel       compression level of the compressor.
     * @param windowSize             maximum size of the window compressor buffer.
     * @param noContext              true to disable context takeover.
     * @param extensionEncoderFilter extension filter for per message deflate encoder.
     */
    PerMessageDeflateEncoder(int compressionLevel, int windowSize, boolean noContext, WebSocketExtensionFilter extensionEncoderFilter) {
        super(compressionLevel, windowSize, noContext, extensionEncoderFilter);
    }

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        if (!super.acceptOutboundMessage(msg)) {
            return false;
        }

        WebSocketFrame wsFrame = (WebSocketFrame) msg;
        if (extensionEncoderFilter().mustSkip(wsFrame)) {
            if (compressing) {
                throw new IllegalStateException("Cannot skip per message deflate encoder, compression in progress");
            }
            return false;
        }

        return ((wsFrame instanceof TextWebSocketFrame || wsFrame instanceof BinaryWebSocketFrame) && (wsFrame.rsv() & WebSocketExtension.RSV1) == 0) || (wsFrame instanceof ContinuationWebSocketFrame && compressing);
    }

    @Override
    protected int rsv(WebSocketFrame msg) {
        return msg instanceof TextWebSocketFrame || msg instanceof BinaryWebSocketFrame ? msg.rsv() | WebSocketExtension.RSV1 : msg.rsv();
    }

    @Override
    protected boolean removeFrameTail(WebSocketFrame msg) {
        return msg.isFinalFragment();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        super.encode(ctx, msg, out);

        if (msg.isFinalFragment()) {
            compressing = false;
        } else if (msg instanceof TextWebSocketFrame || msg instanceof BinaryWebSocketFrame) {
            compressing = true;
        }
    }

}
