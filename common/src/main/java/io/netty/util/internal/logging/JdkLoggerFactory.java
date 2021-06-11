package io.netty.util.internal.logging;


import java.util.logging.Logger;

/**
 * Logger factory which creates a
 * <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/logging/">java.util.logging</a>
 * logger.
 */
public class JdkLoggerFactory extends InternalLoggerFactory {

    public static final InternalLoggerFactory INSTANCE = new JdkLoggerFactory();

    /**
     * @deprecated Use {@link #INSTANCE} instead.
     */
    @Deprecated
    public JdkLoggerFactory() {
    }

    @Override
    public InternalLogger newInstance(String name) {
        return new JdkLogger(Logger.getLogger(name));
    }
}
