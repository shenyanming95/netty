package io.netty.util.concurrent;

import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * {@link GenericFutureListener} implementation which takes other {@link Promise}s
 * and notifies them on completion.
 *
 * @param <V> the type of value returned by the future
 * @param <F> the type of future
 */
public class PromiseNotifier<V, F extends Future<V>> implements GenericFutureListener<F> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PromiseNotifier.class);
    private final Promise<? super V>[] promises;
    private final boolean logNotifyFailure;

    /**
     * Create a new instance.
     *
     * @param promises the {@link Promise}s to notify once this {@link GenericFutureListener} is notified.
     */
    @SafeVarargs
    public PromiseNotifier(Promise<? super V>... promises) {
        this(true, promises);
    }

    /**
     * Create a new instance.
     *
     * @param logNotifyFailure {@code true} if logging should be done in case notification fails.
     * @param promises         the {@link Promise}s to notify once this {@link GenericFutureListener} is notified.
     */
    @SafeVarargs
    public PromiseNotifier(boolean logNotifyFailure, Promise<? super V>... promises) {
        checkNotNull(promises, "promises");
        for (Promise<? super V> promise : promises) {
            if (promise == null) {
                throw new IllegalArgumentException("promises contains null Promise");
            }
        }
        this.promises = promises.clone();
        this.logNotifyFailure = logNotifyFailure;
    }

    @Override
    public void operationComplete(F future) throws Exception {
        InternalLogger internalLogger = logNotifyFailure ? logger : null;
        if (future.isSuccess()) {
            V result = future.get();
            for (Promise<? super V> p : promises) {
                PromiseNotificationUtil.trySuccess(p, result, internalLogger);
            }
        } else if (future.isCancelled()) {
            for (Promise<? super V> p : promises) {
                PromiseNotificationUtil.tryCancel(p, internalLogger);
            }
        } else {
            Throwable cause = future.cause();
            for (Promise<? super V> p : promises) {
                PromiseNotificationUtil.tryFailure(p, cause, internalLogger);
            }
        }
    }
}
