package io.netty.handler.logging;

import io.netty.util.internal.logging.InternalLogLevel;

/**
 * Maps the regular {@link LogLevel}s with the {@link InternalLogLevel} ones.
 */
public enum LogLevel {
    TRACE(InternalLogLevel.TRACE), DEBUG(InternalLogLevel.DEBUG), INFO(InternalLogLevel.INFO), WARN(InternalLogLevel.WARN), ERROR(InternalLogLevel.ERROR);

    private final InternalLogLevel internalLevel;

    LogLevel(InternalLogLevel internalLevel) {
        this.internalLevel = internalLevel;
    }

    /**
     * For internal use only.
     * <p>
     * <p/>Converts the specified {@link LogLevel} to its {@link InternalLogLevel} variant.
     *
     * @return the converted level.
     */
    public InternalLogLevel toInternalLevel() {
        return internalLevel;
    }
}
