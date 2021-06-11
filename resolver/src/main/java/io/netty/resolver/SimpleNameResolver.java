package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.util.List;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * A skeletal {@link NameResolver} implementation.
 */
public abstract class SimpleNameResolver<T> implements NameResolver<T> {

    private final EventExecutor executor;

    /**
     * @param executor the {@link EventExecutor} which is used to notify the listeners of the {@link Future} returned
     *                 by {@link #resolve(String)}
     */
    protected SimpleNameResolver(EventExecutor executor) {
        this.executor = checkNotNull(executor, "executor");
    }

    /**
     * Returns the {@link EventExecutor} which is used to notify the listeners of the {@link Future} returned
     * by {@link #resolve(String)}.
     */
    protected EventExecutor executor() {
        return executor;
    }

    @Override
    public final Future<T> resolve(String inetHost) {
        final Promise<T> promise = executor().newPromise();
        return resolve(inetHost, promise);
    }

    @Override
    public Future<T> resolve(String inetHost, Promise<T> promise) {
        checkNotNull(promise, "promise");

        try {
            doResolve(inetHost, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    @Override
    public final Future<List<T>> resolveAll(String inetHost) {
        final Promise<List<T>> promise = executor().newPromise();
        return resolveAll(inetHost, promise);
    }

    @Override
    public Future<List<T>> resolveAll(String inetHost, Promise<List<T>> promise) {
        checkNotNull(promise, "promise");

        try {
            doResolveAll(inetHost, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    /**
     * Invoked by {@link #resolve(String)} to perform the actual name resolution.
     */
    protected abstract void doResolve(String inetHost, Promise<T> promise) throws Exception;

    /**
     * Invoked by {@link #resolveAll(String)} to perform the actual name resolution.
     */
    protected abstract void doResolveAll(String inetHost, Promise<List<T>> promise) throws Exception;

    @Override
    public void close() {
    }
}
