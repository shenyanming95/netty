package io.netty.channel.unix;

import io.netty.channel.socket.DuplexChannel;

/**
 * A {@link UnixChannel} that supports communication via
 * <a href="http://en.wikipedia.org/wiki/Unix_domain_socket">Unix Domain Socket</a>.
 */
public interface DomainSocketChannel extends UnixChannel, DuplexChannel {
    @Override
    DomainSocketAddress remoteAddress();

    @Override
    DomainSocketAddress localAddress();

    @Override
    DomainSocketChannelConfig config();
}
