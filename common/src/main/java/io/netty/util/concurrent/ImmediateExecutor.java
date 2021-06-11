package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;

import java.util.concurrent.Executor;

/**
 * {@link Executor} which execute tasks in the callers thread.
 */
public final class ImmediateExecutor implements Executor {
    public static final ImmediateExecutor INSTANCE = new ImmediateExecutor();

    private ImmediateExecutor() {
        // use static instance
    }

    @Override
    public void execute(Runnable command) {
        ObjectUtil.checkNotNull(command, "command").run();
    }
}
