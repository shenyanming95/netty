package io.netty.channel;

import io.netty.util.ReferenceCounted;

import java.net.SocketAddress;

/**
 * A message that wraps another message with a sender address and a recipient address.
 *
 * @param <M> the type of the wrapped message
 * @param <A> the type of the address
 */
public interface AddressedEnvelope<M, A extends SocketAddress> extends ReferenceCounted {
    /**
     * Returns the message wrapped by this envelope message.
     */
    M content();

    /**
     * Returns the address of the sender of this message.
     */
    A sender();

    /**
     * Returns the address of the recipient of this message.
     */
    A recipient();

    @Override
    AddressedEnvelope<M, A> retain();

    @Override
    AddressedEnvelope<M, A> retain(int increment);

    @Override
    AddressedEnvelope<M, A> touch();

    @Override
    AddressedEnvelope<M, A> touch(Object hint);
}
