package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilter;

import java.util.List;

import static io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateDecoder.EMPTY_DEFLATE_BLOCK;
import static io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateDecoder.FRAME_TAIL;
import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * Deflate implementation of a payload compressor for
 * <tt>io.netty.handler.codec.http.websocketx.WebSocketFrame</tt>.
 */
abstract class DeflateEncoder extends WebSocketExtensionEncoder {

    private final int compressionLevel;
    private final int windowSize;
    private final boolean noContext;
    private final WebSocketExtensionFilter extensionEncoderFilter;

    private EmbeddedChannel encoder;

    /**
     * Constructor
     *
     * @param compressionLevel       compression level of the compressor.
     * @param windowSize             maximum size of the window compressor buffer.
     * @param noContext              true to disable context takeover.
     * @param extensionEncoderFilter extension encoder filter.
     */
    DeflateEncoder(int compressionLevel, int windowSize, boolean noContext, WebSocketExtensionFilter extensionEncoderFilter) {
        this.compressionLevel = compressionLevel;
        this.windowSize = windowSize;
        this.noContext = noContext;
        this.extensionEncoderFilter = checkNotNull(extensionEncoderFilter, "extensionEncoderFilter");
    }

    /**
     * Returns the extension encoder filter.
     */
    protected WebSocketExtensionFilter extensionEncoderFilter() {
        return extensionEncoderFilter;
    }

    /**
     * @param msg the current frame.
     * @return the rsv bits to set in the compressed frame.
     */
    protected abstract int rsv(WebSocketFrame msg);

    /**
     * @param msg the current frame.
     * @return true if compressed payload tail needs to be removed.
     */
    protected abstract boolean removeFrameTail(WebSocketFrame msg);

    @Override
    protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        final ByteBuf compressedContent;
        if (msg.content().isReadable()) {
            compressedContent = compressContent(ctx, msg);
        } else if (msg.isFinalFragment()) {
            // Set empty DEFLATE block manually for unknown buffer size
            // https://tools.ietf.org/html/rfc7692#section-7.2.3.6
            compressedContent = EMPTY_DEFLATE_BLOCK.duplicate();
        } else {
            throw new CodecException("cannot compress content buffer");
        }

        final WebSocketFrame outMsg;
        if (msg instanceof TextWebSocketFrame) {
            outMsg = new TextWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
        } else if (msg instanceof BinaryWebSocketFrame) {
            outMsg = new BinaryWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
        } else if (msg instanceof ContinuationWebSocketFrame) {
            outMsg = new ContinuationWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
        } else {
            throw new CodecException("unexpected frame type: " + msg.getClass().getName());
        }

        out.add(outMsg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.handlerRemoved(ctx);
    }

    private ByteBuf compressContent(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (encoder == null) {
            encoder = new EmbeddedChannel(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.NONE, compressionLevel, windowSize, 8));
        }

        encoder.writeOutbound(msg.content().retain());

        CompositeByteBuf fullCompressedContent = ctx.alloc().compositeBuffer();
        for (; ; ) {
            ByteBuf partCompressedContent = encoder.readOutbound();
            if (partCompressedContent == null) {
                break;
            }
            if (!partCompressedContent.isReadable()) {
                partCompressedContent.release();
                continue;
            }
            fullCompressedContent.addComponent(true, partCompressedContent);
        }

        if (fullCompressedContent.numComponents() <= 0) {
            fullCompressedContent.release();
            throw new CodecException("cannot read compressed buffer");
        }

        if (msg.isFinalFragment() && noContext) {
            cleanup();
        }

        ByteBuf compressedContent;
        if (removeFrameTail(msg)) {
            int realLength = fullCompressedContent.readableBytes() - FRAME_TAIL.readableBytes();
            compressedContent = fullCompressedContent.slice(0, realLength);
        } else {
            compressedContent = fullCompressedContent;
        }

        return compressedContent;
    }

    private void cleanup() {
        if (encoder != null) {
            // Clean-up the previous encoder if not cleaned up correctly.
            encoder.finishAndReleaseAll();
            encoder = null;
        }
    }
}
