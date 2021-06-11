package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol SYN_STREAM Frame
 */
public interface SpdySynStreamFrame extends SpdyHeadersFrame {

    /**
     * Returns the Associated-To-Stream-ID of this frame.
     */
    int associatedStreamId();

    /**
     * Sets the Associated-To-Stream-ID of this frame.
     * The Associated-To-Stream-ID cannot be negative.
     */
    SpdySynStreamFrame setAssociatedStreamId(int associatedStreamId);

    /**
     * Returns the priority of the stream.
     */
    byte priority();

    /**
     * Sets the priority of the stream.
     * The priority must be between 0 and 7 inclusive.
     */
    SpdySynStreamFrame setPriority(byte priority);

    /**
     * Returns {@code true} if the stream created with this frame is to be
     * considered half-closed to the receiver.
     */
    boolean isUnidirectional();

    /**
     * Sets if the stream created with this frame is to be considered
     * half-closed to the receiver.
     */
    SpdySynStreamFrame setUnidirectional(boolean unidirectional);

    @Override
    SpdySynStreamFrame setStreamId(int streamID);

    @Override
    SpdySynStreamFrame setLast(boolean last);

    @Override
    SpdySynStreamFrame setInvalid();
}
