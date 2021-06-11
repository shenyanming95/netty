package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;

import java.net.InetSocketAddress;

/**
 * A {@link DnsNameResolverException} raised when {@link DnsNameResolver} failed to perform a successful query because
 * of an timeout. In this case you may want to retry the operation.
 */
public final class DnsNameResolverTimeoutException extends DnsNameResolverException {
    private static final long serialVersionUID = -8826717969627131854L;

    public DnsNameResolverTimeoutException(InetSocketAddress remoteAddress, DnsQuestion question, String message) {
        super(remoteAddress, question, message);
    }
}
