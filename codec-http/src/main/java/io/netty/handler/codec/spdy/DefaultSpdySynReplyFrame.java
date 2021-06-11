package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/**
 * The default {@link SpdySynReplyFrame} implementation.
 */
public class DefaultSpdySynReplyFrame extends DefaultSpdyHeadersFrame implements SpdySynReplyFrame {

    /**
     * Creates a new instance.
     *
     * @param streamId the Stream-ID of this frame
     */
    public DefaultSpdySynReplyFrame(int streamId) {
        super(streamId);
    }

    /**
     * Creates a new instance.
     *
     * @param streamId        the Stream-ID of this frame
     * @param validateHeaders validate the header names and values when adding them to the {@link SpdyHeaders}
     */
    public DefaultSpdySynReplyFrame(int streamId, boolean validateHeaders) {
        super(streamId, validateHeaders);
    }

    @Override
    public SpdySynReplyFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    @Override
    public SpdySynReplyFrame setLast(boolean last) {
        super.setLast(last);
        return this;
    }

    @Override
    public SpdySynReplyFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder().append(StringUtil.simpleClassName(this)).append("(last: ").append(isLast()).append(')').append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(streamId()).append(StringUtil.NEWLINE).append("--> Headers:").append(StringUtil.NEWLINE);
        appendHeaders(buf);

        // Remove the last newline.
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
