package io.netty.channel.unix;

import io.netty.channel.ServerChannel;

/**
 * {@link ServerChannel} that accepts {@link DomainSocketChannel}'s via
 * <a href="http://en.wikipedia.org/wiki/Unix_domain_socket">Unix Domain Socket</a>.
 */
public interface ServerDomainSocketChannel extends ServerChannel, UnixChannel {
    @Override
    DomainSocketAddress remoteAddress();

    @Override
    DomainSocketAddress localAddress();
}
