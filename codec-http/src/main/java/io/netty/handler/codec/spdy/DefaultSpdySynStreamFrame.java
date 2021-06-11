package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

/**
 * The default {@link SpdySynStreamFrame} implementation.
 */
public class DefaultSpdySynStreamFrame extends DefaultSpdyHeadersFrame implements SpdySynStreamFrame {

    private int associatedStreamId;
    private byte priority;
    private boolean unidirectional;

    /**
     * Creates a new instance.
     *
     * @param streamId           the Stream-ID of this frame
     * @param associatedStreamId the Associated-To-Stream-ID of this frame
     * @param priority           the priority of the stream
     */
    public DefaultSpdySynStreamFrame(int streamId, int associatedStreamId, byte priority) {
        this(streamId, associatedStreamId, priority, true);
    }

    /**
     * Creates a new instance.
     *
     * @param streamId           the Stream-ID of this frame
     * @param associatedStreamId the Associated-To-Stream-ID of this frame
     * @param priority           the priority of the stream
     * @param validateHeaders    validate the header names and values when adding them to the {@link SpdyHeaders}
     */
    public DefaultSpdySynStreamFrame(int streamId, int associatedStreamId, byte priority, boolean validateHeaders) {
        super(streamId, validateHeaders);
        setAssociatedStreamId(associatedStreamId);
        setPriority(priority);
    }

    @Override
    public SpdySynStreamFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    @Override
    public SpdySynStreamFrame setLast(boolean last) {
        super.setLast(last);
        return this;
    }

    @Override
    public SpdySynStreamFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override
    public int associatedStreamId() {
        return associatedStreamId;
    }

    @Override
    public SpdySynStreamFrame setAssociatedStreamId(int associatedStreamId) {
        checkPositiveOrZero(associatedStreamId, "associatedStreamId");
        this.associatedStreamId = associatedStreamId;
        return this;
    }

    @Override
    public byte priority() {
        return priority;
    }

    @Override
    public SpdySynStreamFrame setPriority(byte priority) {
        if (priority < 0 || priority > 7) {
            throw new IllegalArgumentException("Priority must be between 0 and 7 inclusive: " + priority);
        }
        this.priority = priority;
        return this;
    }

    @Override
    public boolean isUnidirectional() {
        return unidirectional;
    }

    @Override
    public SpdySynStreamFrame setUnidirectional(boolean unidirectional) {
        this.unidirectional = unidirectional;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder().append(StringUtil.simpleClassName(this)).append("(last: ").append(isLast()).append("; unidirectional: ").append(isUnidirectional()).append(')').append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(streamId()).append(StringUtil.NEWLINE);
        if (associatedStreamId != 0) {
            buf.append("--> Associated-To-Stream-ID = ").append(associatedStreamId()).append(StringUtil.NEWLINE);
        }
        buf.append("--> Priority = ").append(priority()).append(StringUtil.NEWLINE).append("--> Headers:").append(StringUtil.NEWLINE);
        appendHeaders(buf);

        // Remove the last newline.
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
