package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;

/**
 * An STOMP chunk which is used for STOMP chunked transfer-encoding. {@link StompSubframeDecoder} generates
 * {@link StompContentSubframe} after {@link StompHeadersSubframe} when the content is large or the encoding of
 * the content is 'chunked.  If you prefer not to receive multiple {@link StompSubframe}s for a single
 * {@link StompFrame}, place {@link StompSubframeAggregator} after {@link StompSubframeDecoder} in the
 * {@link ChannelPipeline}.
 */
public interface StompContentSubframe extends ByteBufHolder, StompSubframe {
    @Override
    StompContentSubframe copy();

    @Override
    StompContentSubframe duplicate();

    @Override
    StompContentSubframe retainedDuplicate();

    @Override
    StompContentSubframe replace(ByteBuf content);

    @Override
    StompContentSubframe retain();

    @Override
    StompContentSubframe retain(int increment);

    @Override
    StompContentSubframe touch();

    @Override
    StompContentSubframe touch(Object hint);
}
