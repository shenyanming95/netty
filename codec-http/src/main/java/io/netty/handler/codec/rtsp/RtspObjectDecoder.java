package io.netty.handler.codec.rtsp;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;

import static io.netty.handler.codec.rtsp.RtspDecoder.DEFAULT_MAX_CONTENT_LENGTH;

/**
 * Decodes {@link ByteBuf}s into RTSP messages represented in
 * {@link HttpMessage}s.
 * <p>
 * <h3>Parameters that prevents excessive memory consumption</h3>
 * <table border="1">
 * <tr>
 * <th>Name</th><th>Meaning</th>
 * </tr>
 * <tr>
 * <td>{@code maxInitialLineLength}</td>
 * <td>The maximum length of the initial line
 *     (e.g. {@code "SETUP / RTSP/1.0"} or {@code "RTSP/1.0 200 OK"})
 *     If the length of the initial line exceeds this value, a
 *     {@link TooLongFrameException} will be raised.</td>
 * </tr>
 * <tr>
 * <td>{@code maxHeaderSize}</td>
 * <td>The maximum length of all headers.  If the sum of the length of each
 *     header exceeds this value, a {@link TooLongFrameException} will be raised.</td>
 * </tr>
 * <tr>
 * <td>{@code maxContentLength}</td>
 * <td>The maximum length of the content.  If the content length exceeds this
 *     value, a {@link TooLongFrameException} will be raised.</td>
 * </tr>
 * </table>
 *
 * @deprecated Use {@link RtspDecoder} instead.
 */
@Deprecated
public abstract class RtspObjectDecoder extends HttpObjectDecoder {

    /**
     * Creates a new instance with the default
     * {@code maxInitialLineLength (4096)}, {@code maxHeaderSize (8192)}, and
     * {@code maxContentLength (8192)}.
     */
    protected RtspObjectDecoder() {
        this(DEFAULT_MAX_INITIAL_LINE_LENGTH, DEFAULT_MAX_HEADER_SIZE, DEFAULT_MAX_CONTENT_LENGTH);
    }

    /**
     * Creates a new instance with the specified parameters.
     */
    protected RtspObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength) {
        super(maxInitialLineLength, maxHeaderSize, maxContentLength * 2, false);
    }

    protected RtspObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength, boolean validateHeaders) {
        super(maxInitialLineLength, maxHeaderSize, maxContentLength * 2, false, validateHeaders);
    }

    @Override
    protected boolean isContentAlwaysEmpty(HttpMessage msg) {
        // Unlike HTTP, RTSP always assumes zero-length body if Content-Length
        // header is absent.
        boolean empty = super.isContentAlwaysEmpty(msg);
        if (empty) {
            return true;
        }
        if (!msg.headers().contains(RtspHeaderNames.CONTENT_LENGTH)) {
            return true;
        }
        return empty;
    }
}
