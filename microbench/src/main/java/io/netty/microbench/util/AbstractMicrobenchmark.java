package io.netty.microbench.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of the JMH microbenchmark adapter.  There may be context switches introduced by this harness.
 */
@Fork(AbstractMicrobenchmark.DEFAULT_FORKS)
public class AbstractMicrobenchmark extends AbstractMicrobenchmarkBase {

    protected static final int DEFAULT_FORKS = 2;
    private final String[] jvmArgs;

    public AbstractMicrobenchmark() {
        this(false, false);
    }

    public AbstractMicrobenchmark(boolean disableAssertions) {
        this(disableAssertions, false);
    }

    public AbstractMicrobenchmark(boolean disableAssertions, boolean disableHarnessExecutor) {
        final String[] customArgs;
        if (disableHarnessExecutor) {
            customArgs = new String[]{"-Xms768m", "-Xmx768m", "-XX:MaxDirectMemorySize=768m", "-XX:BiasedLockingStartupDelay=0"};
        } else {
            customArgs = new String[]{"-Xms768m", "-Xmx768m", "-XX:MaxDirectMemorySize=768m", "-XX:BiasedLockingStartupDelay=0", "-Djmh.executor=CUSTOM", "-Djmh.executor.class=io.netty.microbench.util.AbstractMicrobenchmark$HarnessExecutor"};
        }
        String[] jvmArgs = new String[BASE_JVM_ARGS.length + customArgs.length];
        System.arraycopy(BASE_JVM_ARGS, 0, jvmArgs, 0, BASE_JVM_ARGS.length);
        System.arraycopy(customArgs, 0, jvmArgs, BASE_JVM_ARGS.length, customArgs.length);
        if (disableAssertions) {
            jvmArgs = removeAssertions(jvmArgs);
        }
        this.jvmArgs = jvmArgs;
    }

    @Override
    protected String[] jvmArgs() {
        return jvmArgs;
    }

    @Override
    protected ChainedOptionsBuilder newOptionsBuilder() throws Exception {
        ChainedOptionsBuilder runnerOptions = super.newOptionsBuilder();
        if (getForks() > 0) {
            runnerOptions.forks(getForks());
        }

        return runnerOptions;
    }

    protected int getForks() {
        return SystemPropertyUtil.getInt("forks", -1);
    }

    public static final class HarnessExecutor extends ThreadPoolExecutor {
        private final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractMicrobenchmark.class);

        public HarnessExecutor(int maxThreads, String prefix) {
            super(maxThreads, maxThreads, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory(prefix));
            logger.debug("Using harness executor");
        }
    }
}
