package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;

/**
 * A {@link AddressResolver} that does not perform any resolution but always reports successful resolution.
 * This resolver is useful when name resolution is performed by a handler in a pipeline, such as a proxy handler.
 */
public class NoopAddressResolver extends AbstractAddressResolver<SocketAddress> {

    public NoopAddressResolver(EventExecutor executor) {
        super(executor);
    }

    @Override
    protected boolean doIsResolved(SocketAddress address) {
        return true;
    }

    @Override
    protected void doResolve(SocketAddress unresolvedAddress, Promise<SocketAddress> promise) throws Exception {
        promise.setSuccess(unresolvedAddress);
    }

    @Override
    protected void doResolveAll(SocketAddress unresolvedAddress, Promise<List<SocketAddress>> promise) throws Exception {
        promise.setSuccess(Collections.singletonList(unresolvedAddress));
    }
}
