package io.netty.resolver;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.io.Closeable;
import java.util.List;

/**
 * Resolves an arbitrary string that represents the name of an endpoint into an address.
 */
public interface NameResolver<T> extends Closeable {

    /**
     * Resolves the specified name into an address.
     *
     * @param inetHost the name to resolve
     * @return the address as the result of the resolution
     */
    Future<T> resolve(String inetHost);

    /**
     * Resolves the specified name into an address.
     *
     * @param inetHost the name to resolve
     * @param promise  the {@link Promise} which will be fulfilled when the name resolution is finished
     * @return the address as the result of the resolution
     */
    Future<T> resolve(String inetHost, Promise<T> promise);

    /**
     * Resolves the specified host name and port into a list of address.
     *
     * @param inetHost the name to resolve
     * @return the list of the address as the result of the resolution
     */
    Future<List<T>> resolveAll(String inetHost);

    /**
     * Resolves the specified host name and port into a list of address.
     *
     * @param inetHost the name to resolve
     * @param promise  the {@link Promise} which will be fulfilled when the name resolution is finished
     * @return the list of the address as the result of the resolution
     */
    Future<List<T>> resolveAll(String inetHost, Promise<List<T>> promise);

    /**
     * Closes all the resources allocated and used by this resolver.
     */
    @Override
    void close();
}
