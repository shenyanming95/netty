package io.netty.channel.epoll;

import io.netty.channel.unix.FileDescriptor;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * Tells if <a href="https://netty.io/wiki/native-transports.html">{@code netty-transport-native-epoll}</a> is
 * supported.
 */
public final class Epoll {

    private static final Throwable UNAVAILABILITY_CAUSE;

    static {
        Throwable cause = null;

        if (SystemPropertyUtil.getBoolean("io.netty.transport.noNative", false)) {
            cause = new UnsupportedOperationException("Native transport was explicit disabled with -Dio.netty.transport.noNative=true");
        } else {
            FileDescriptor epollFd = null;
            FileDescriptor eventFd = null;
            try {
                epollFd = Native.newEpollCreate();
                eventFd = Native.newEventFd();
            } catch (Throwable t) {
                cause = t;
            } finally {
                if (epollFd != null) {
                    try {
                        epollFd.close();
                    } catch (Exception ignore) {
                        // ignore
                    }
                }
                if (eventFd != null) {
                    try {
                        eventFd.close();
                    } catch (Exception ignore) {
                        // ignore
                    }
                }
            }
        }

        UNAVAILABILITY_CAUSE = cause;
    }

    private Epoll() {
    }

    /**
     * Returns {@code true} if and only if the <a href="https://netty.io/wiki/native-transports.html">{@code
     * netty-transport-native-epoll}</a> is available.
     */
    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    /**
     * Ensure that <a href="https://netty.io/wiki/native-transports.html">{@code netty-transport-native-epoll}</a> is
     * available.
     *
     * @throws UnsatisfiedLinkError if unavailable
     */
    public static void ensureAvailability() {
        if (UNAVAILABILITY_CAUSE != null) {
            throw (Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE);
        }
    }

    /**
     * Returns the cause of unavailability of <a href="https://netty.io/wiki/native-transports.html">
     * {@code netty-transport-native-epoll}</a>.
     *
     * @return the cause if unavailable. {@code null} if available.
     */
    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }
}
