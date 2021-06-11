package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link AbstractAddressResolver} that resolves {@link InetSocketAddress}.
 */
public class InetSocketAddressResolver extends AbstractAddressResolver<InetSocketAddress> {

    final NameResolver<InetAddress> nameResolver;

    /**
     * @param executor     the {@link EventExecutor} which is used to notify the listeners of the {@link Future} returned
     *                     by {@link #resolve(java.net.SocketAddress)}
     * @param nameResolver the {@link NameResolver} used for name resolution
     */
    public InetSocketAddressResolver(EventExecutor executor, NameResolver<InetAddress> nameResolver) {
        super(executor, InetSocketAddress.class);
        this.nameResolver = nameResolver;
    }

    @Override
    protected boolean doIsResolved(InetSocketAddress address) {
        return !address.isUnresolved();
    }

    @Override
    protected void doResolve(final InetSocketAddress unresolvedAddress, final Promise<InetSocketAddress> promise) throws Exception {
        // Note that InetSocketAddress.getHostName() will never incur a reverse lookup here,
        // because an unresolved address always has a host name.
        nameResolver.resolve(unresolvedAddress.getHostName()).addListener(new FutureListener<InetAddress>() {
            @Override
            public void operationComplete(Future<InetAddress> future) throws Exception {
                if (future.isSuccess()) {
                    promise.setSuccess(new InetSocketAddress(future.getNow(), unresolvedAddress.getPort()));
                } else {
                    promise.setFailure(future.cause());
                }
            }
        });
    }

    @Override
    protected void doResolveAll(final InetSocketAddress unresolvedAddress, final Promise<List<InetSocketAddress>> promise) throws Exception {
        // Note that InetSocketAddress.getHostName() will never incur a reverse lookup here,
        // because an unresolved address always has a host name.
        nameResolver.resolveAll(unresolvedAddress.getHostName()).addListener(new FutureListener<List<InetAddress>>() {
            @Override
            public void operationComplete(Future<List<InetAddress>> future) throws Exception {
                if (future.isSuccess()) {
                    List<InetAddress> inetAddresses = future.getNow();
                    List<InetSocketAddress> socketAddresses = new ArrayList<InetSocketAddress>(inetAddresses.size());
                    for (InetAddress inetAddress : inetAddresses) {
                        socketAddresses.add(new InetSocketAddress(inetAddress, unresolvedAddress.getPort()));
                    }
                    promise.setSuccess(socketAddresses);
                } else {
                    promise.setFailure(future.cause());
                }
            }
        });
    }

    @Override
    public void close() {
        nameResolver.close();
    }
}
