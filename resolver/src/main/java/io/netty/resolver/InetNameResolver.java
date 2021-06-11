package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * A skeletal {@link NameResolver} implementation that resolves {@link InetAddress}.
 */
public abstract class InetNameResolver extends SimpleNameResolver<InetAddress> {
    private volatile AddressResolver<InetSocketAddress> addressResolver;

    /**
     * @param executor the {@link EventExecutor} which is used to notify the listeners of the {@link Future} returned
     *                 by {@link #resolve(String)}
     */
    protected InetNameResolver(EventExecutor executor) {
        super(executor);
    }

    /**
     * Return a {@link AddressResolver} that will use this name resolver underneath.
     * It's cached internally, so the same instance is always returned.
     */
    public AddressResolver<InetSocketAddress> asAddressResolver() {
        AddressResolver<InetSocketAddress> result = addressResolver;
        if (result == null) {
            synchronized (this) {
                result = addressResolver;
                if (result == null) {
                    addressResolver = result = new InetSocketAddressResolver(executor(), this);
                }
            }
        }
        return result;
    }
}
