package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;

/**
 * Shared Static object between HttpMessageDecoder, HttpPostRequestDecoder and HttpPostRequestEncoder
 */
final class HttpPostBodyUtil {

    public static final int chunkSize = 8096;

    /**
     * Default Content-Type in binary form
     */
    public static final String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";

    /**
     * Default Content-Type in Text form
     */
    public static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";

    private HttpPostBodyUtil() {
    }

    /**
     * Find the first non whitespace
     *
     * @return the rank of the first non whitespace
     */
    static int findNonWhitespace(String sb, int offset) {
        int result;
        for (result = offset; result < sb.length(); result++) {
            if (!Character.isWhitespace(sb.charAt(result))) {
                break;
            }
        }
        return result;
    }

    /**
     * Find the end of String
     *
     * @return the rank of the end of string
     */
    static int findEndOfString(String sb) {
        int result;
        for (result = sb.length(); result > 0; result--) {
            if (!Character.isWhitespace(sb.charAt(result - 1))) {
                break;
            }
        }
        return result;
    }

    /**
     * Allowed mechanism for multipart
     * mechanism := "7bit"
     * / "8bit"
     * / "binary"
     * Not allowed: "quoted-printable"
     * / "base64"
     */
    public enum TransferEncodingMechanism {
        /**
         * Default encoding
         */
        BIT7("7bit"),
        /**
         * Short lines but not in ASCII - no encoding
         */
        BIT8("8bit"),
        /**
         * Could be long text not in ASCII - no encoding
         */
        BINARY("binary");

        private final String value;

        TransferEncodingMechanism(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * This class intends to decrease the CPU in seeking ahead some bytes in
     * HttpPostRequestDecoder
     */
    static class SeekAheadOptimize {
        byte[] bytes;
        int readerIndex;
        int pos;
        int origPos;
        int limit;
        ByteBuf buffer;

        /**
         * @param buffer buffer with a backing byte array
         */
        SeekAheadOptimize(ByteBuf buffer) {
            if (!buffer.hasArray()) {
                throw new IllegalArgumentException("buffer hasn't backing byte array");
            }
            this.buffer = buffer;
            bytes = buffer.array();
            readerIndex = buffer.readerIndex();
            origPos = pos = buffer.arrayOffset() + readerIndex;
            limit = buffer.arrayOffset() + buffer.writerIndex();
        }

        /**
         * @param minus this value will be used as (currentPos - minus) to set
         *              the current readerIndex in the buffer.
         */
        void setReadPosition(int minus) {
            pos -= minus;
            readerIndex = getReadPosition(pos);
            buffer.readerIndex(readerIndex);
        }

        /**
         * @param index raw index of the array (pos in general)
         * @return the value equivalent of raw index to be used in readerIndex(value)
         */
        int getReadPosition(int index) {
            return index - origPos + readerIndex;
        }
    }

}
