package io.netty.handler.codec.spdy;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * A combination of {@link SpdyHttpDecoder} and {@link SpdyHttpEncoder}
 */
public final class SpdyHttpCodec extends CombinedChannelDuplexHandler<SpdyHttpDecoder, SpdyHttpEncoder> {
    /**
     * Creates a new instance with the specified decoder options.
     */
    public SpdyHttpCodec(SpdyVersion version, int maxContentLength) {
        super(new SpdyHttpDecoder(version, maxContentLength), new SpdyHttpEncoder(version));
    }

    /**
     * Creates a new instance with the specified decoder options.
     */
    public SpdyHttpCodec(SpdyVersion version, int maxContentLength, boolean validateHttpHeaders) {
        super(new SpdyHttpDecoder(version, maxContentLength, validateHttpHeaders), new SpdyHttpEncoder(version));
    }
}
