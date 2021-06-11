package io.netty.resolver.dns;

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsResponseCode;

import java.net.InetSocketAddress;
import java.util.List;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * Combines two {@link DnsQueryLifecycleObserver} into a single {@link DnsQueryLifecycleObserver}.
 */
public final class BiDnsQueryLifecycleObserver implements DnsQueryLifecycleObserver {
    private final DnsQueryLifecycleObserver a;
    private final DnsQueryLifecycleObserver b;

    /**
     * Create a new instance.
     *
     * @param a The {@link DnsQueryLifecycleObserver} that will receive events first.
     * @param b The {@link DnsQueryLifecycleObserver} that will receive events second.
     */
    public BiDnsQueryLifecycleObserver(DnsQueryLifecycleObserver a, DnsQueryLifecycleObserver b) {
        this.a = checkNotNull(a, "a");
        this.b = checkNotNull(b, "b");
    }

    @Override
    public void queryWritten(InetSocketAddress dnsServerAddress, ChannelFuture future) {
        try {
            a.queryWritten(dnsServerAddress, future);
        } finally {
            b.queryWritten(dnsServerAddress, future);
        }
    }

    @Override
    public void queryCancelled(int queriesRemaining) {
        try {
            a.queryCancelled(queriesRemaining);
        } finally {
            b.queryCancelled(queriesRemaining);
        }
    }

    @Override
    public DnsQueryLifecycleObserver queryRedirected(List<InetSocketAddress> nameServers) {
        try {
            a.queryRedirected(nameServers);
        } finally {
            b.queryRedirected(nameServers);
        }
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryCNAMEd(DnsQuestion cnameQuestion) {
        try {
            a.queryCNAMEd(cnameQuestion);
        } finally {
            b.queryCNAMEd(cnameQuestion);
        }
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryNoAnswer(DnsResponseCode code) {
        try {
            a.queryNoAnswer(code);
        } finally {
            b.queryNoAnswer(code);
        }
        return this;
    }

    @Override
    public void queryFailed(Throwable cause) {
        try {
            a.queryFailed(cause);
        } finally {
            b.queryFailed(cause);
        }
    }

    @Override
    public void querySucceed() {
        try {
            a.querySucceed();
        } finally {
            b.querySucceed();
        }
    }
}
