package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;

/**
 * Combines {@link StompHeadersSubframe} and {@link LastStompContentSubframe} into one
 * frame. So it represent a <i>complete</i> STOMP frame.
 */
public interface StompFrame extends StompHeadersSubframe, LastStompContentSubframe {
    @Override
    StompFrame copy();

    @Override
    StompFrame duplicate();

    @Override
    StompFrame retainedDuplicate();

    @Override
    StompFrame replace(ByteBuf content);

    @Override
    StompFrame retain();

    @Override
    StompFrame retain(int increment);

    @Override
    StompFrame touch();

    @Override
    StompFrame touch(Object hint);
}
