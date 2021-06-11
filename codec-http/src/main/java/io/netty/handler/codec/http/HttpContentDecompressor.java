package io.netty.handler.codec.http;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

import static io.netty.handler.codec.http.HttpHeaderValues.*;

/**
 * Decompresses an {@link HttpMessage} and an {@link HttpContent} compressed in
 * {@code gzip} or {@code deflate} encoding.  For more information on how this
 * handler modifies the message, please refer to {@link HttpContentDecoder}.
 */
public class HttpContentDecompressor extends HttpContentDecoder {

    private final boolean strict;

    /**
     * Create a new {@link HttpContentDecompressor} in non-strict mode.
     */
    public HttpContentDecompressor() {
        this(false);
    }

    /**
     * Create a new {@link HttpContentDecompressor}.
     *
     * @param strict if {@code true} use strict handling of deflate if used, otherwise handle it in a
     *               more lenient fashion.
     */
    public HttpContentDecompressor(boolean strict) {
        this.strict = strict;
    }

    @Override
    protected EmbeddedChannel newContentDecoder(String contentEncoding) throws Exception {
        if (GZIP.contentEqualsIgnoreCase(contentEncoding) || X_GZIP.contentEqualsIgnoreCase(contentEncoding)) {
            return new EmbeddedChannel(ctx.channel().id(), ctx.channel().metadata().hasDisconnect(), ctx.channel().config(), ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
        }
        if (DEFLATE.contentEqualsIgnoreCase(contentEncoding) || X_DEFLATE.contentEqualsIgnoreCase(contentEncoding)) {
            final ZlibWrapper wrapper = strict ? ZlibWrapper.ZLIB : ZlibWrapper.ZLIB_OR_NONE;
            // To be strict, 'deflate' means ZLIB, but some servers were not implemented correctly.
            return new EmbeddedChannel(ctx.channel().id(), ctx.channel().metadata().hasDisconnect(), ctx.channel().config(), ZlibCodecFactory.newZlibDecoder(wrapper));
        }

        // 'identity' or unsupported
        return null;
    }
}
