package io.netty.handler.codec.spdy;

/**
 * A SPDY Protocol SYN_REPLY Frame
 */
public interface SpdySynReplyFrame extends SpdyHeadersFrame {

    @Override
    SpdySynReplyFrame setStreamId(int streamID);

    @Override
    SpdySynReplyFrame setLast(boolean last);

    @Override
    SpdySynReplyFrame setInvalid();
}
