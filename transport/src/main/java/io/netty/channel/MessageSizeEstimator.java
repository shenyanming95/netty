package io.netty.channel;

/**
 * Responsible to estimate the size of a message. The size represents approximately how much memory the message will
 * reserve in memory.
 */
public interface MessageSizeEstimator {

    /**
     * Creates a new handle. The handle provides the actual operations.
     */
    Handle newHandle();

    interface Handle {

        /**
         * Calculate the size of the given message.
         *
         * @param msg The message for which the size should be calculated
         * @return size     The size in bytes. The returned size must be >= 0
         */
        int size(Object msg);
    }
}
