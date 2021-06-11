package io.netty.util.internal.logging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Logger factory which creates a <a href="http://www.slf4j.org/">SLF4J</a>
 * logger.
 */
public class Slf4JLoggerFactory extends InternalLoggerFactory {

    @SuppressWarnings("deprecation")
    public static final InternalLoggerFactory INSTANCE = new Slf4JLoggerFactory();

    /**
     * @deprecated Use {@link #INSTANCE} instead.
     */
    @Deprecated
    public Slf4JLoggerFactory() {
    }

    Slf4JLoggerFactory(boolean failIfNOP) {
        assert failIfNOP; // Should be always called with true.
        if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
            throw new NoClassDefFoundError("NOPLoggerFactory not supported");
        }
    }

    // package-private for testing.
    static InternalLogger wrapLogger(Logger logger) {
        return logger instanceof LocationAwareLogger ? new LocationAwareSlf4JLogger((LocationAwareLogger) logger) : new Slf4JLogger(logger);
    }

    @Override
    public InternalLogger newInstance(String name) {
        return wrapLogger(LoggerFactory.getLogger(name));
    }
}
