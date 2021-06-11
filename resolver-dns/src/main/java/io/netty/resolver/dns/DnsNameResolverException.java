package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;

import java.net.InetSocketAddress;

/**
 * A {@link RuntimeException} raised when {@link DnsNameResolver} failed to perform a successful query.
 */
public class DnsNameResolverException extends RuntimeException {

    private static final long serialVersionUID = -8826717909627131850L;

    private final InetSocketAddress remoteAddress;
    private final DnsQuestion question;

    public DnsNameResolverException(InetSocketAddress remoteAddress, DnsQuestion question, String message) {
        super(message);
        this.remoteAddress = validateRemoteAddress(remoteAddress);
        this.question = validateQuestion(question);
    }

    public DnsNameResolverException(InetSocketAddress remoteAddress, DnsQuestion question, String message, Throwable cause) {
        super(message, cause);
        this.remoteAddress = validateRemoteAddress(remoteAddress);
        this.question = validateQuestion(question);
    }

    private static InetSocketAddress validateRemoteAddress(InetSocketAddress remoteAddress) {
        return ObjectUtil.checkNotNull(remoteAddress, "remoteAddress");
    }

    private static DnsQuestion validateQuestion(DnsQuestion question) {
        return ObjectUtil.checkNotNull(question, "question");
    }

    /**
     * Returns the {@link InetSocketAddress} of the DNS query that has failed.
     */
    public InetSocketAddress remoteAddress() {
        return remoteAddress;
    }

    /**
     * Returns the {@link DnsQuestion} of the DNS query that has failed.
     */
    public DnsQuestion question() {
        return question;
    }

    @Override
    public Throwable fillInStackTrace() {
        setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        return this;
    }
}
