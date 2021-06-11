package io.netty.util.internal.logging;


import org.apache.commons.logging.LogFactory;

/**
 * Logger factory which creates an
 * <a href="http://commons.apache.org/logging/">Apache Commons Logging</a>
 * logger.
 *
 * @deprecated Please use {@link Log4J2LoggerFactory} or {@link Log4JLoggerFactory} or
 * {@link Slf4JLoggerFactory}.
 */
@Deprecated
public class CommonsLoggerFactory extends InternalLoggerFactory {

    public static final InternalLoggerFactory INSTANCE = new CommonsLoggerFactory();

    /**
     * @deprecated Use {@link #INSTANCE} instead.
     */
    @Deprecated
    public CommonsLoggerFactory() {
    }

    @Override
    public InternalLogger newInstance(String name) {
        return new CommonsLogger(LogFactory.getLog(name), name);
    }
}
