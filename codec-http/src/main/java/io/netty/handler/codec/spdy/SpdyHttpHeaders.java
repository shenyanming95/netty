package io.netty.handler.codec.spdy;

import io.netty.util.AsciiString;

/**
 * Provides the constants for the header names and the utility methods
 * used by the {@link SpdyHttpDecoder} and {@link SpdyHttpEncoder}.
 */
public final class SpdyHttpHeaders {

    private SpdyHttpHeaders() {
    }

    /**
     * SPDY HTTP header names
     */
    public static final class Names {
        /**
         * {@code "x-spdy-stream-id"}
         */
        public static final AsciiString STREAM_ID = AsciiString.cached("x-spdy-stream-id");
        /**
         * {@code "x-spdy-associated-to-stream-id"}
         */
        public static final AsciiString ASSOCIATED_TO_STREAM_ID = AsciiString.cached("x-spdy-associated-to-stream-id");
        /**
         * {@code "x-spdy-priority"}
         */
        public static final AsciiString PRIORITY = AsciiString.cached("x-spdy-priority");
        /**
         * {@code "x-spdy-scheme"}
         */
        public static final AsciiString SCHEME = AsciiString.cached("x-spdy-scheme");

        private Names() {
        }
    }
}
