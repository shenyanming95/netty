package io.netty.util;

import java.util.Arrays;

/**
 * @deprecated This class will be removed in the future version.
 */
@Deprecated
public class ResourceLeakException extends RuntimeException {

    private static final long serialVersionUID = 7186453858343358280L;

    private final StackTraceElement[] cachedStackTrace;

    public ResourceLeakException() {
        cachedStackTrace = getStackTrace();
    }

    public ResourceLeakException(String message) {
        super(message);
        cachedStackTrace = getStackTrace();
    }

    public ResourceLeakException(String message, Throwable cause) {
        super(message, cause);
        cachedStackTrace = getStackTrace();
    }

    public ResourceLeakException(Throwable cause) {
        super(cause);
        cachedStackTrace = getStackTrace();
    }

    @Override
    public int hashCode() {
        StackTraceElement[] trace = cachedStackTrace;
        int hashCode = 0;
        for (StackTraceElement e : trace) {
            hashCode = hashCode * 31 + e.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResourceLeakException)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        return Arrays.equals(cachedStackTrace, ((ResourceLeakException) o).cachedStackTrace);
    }
}
