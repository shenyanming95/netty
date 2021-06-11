package io.netty.channel;

import java.net.ConnectException;

/**
 * {@link ConnectException} which will be thrown if a connection could
 * not be established because of a connection timeout.
 */
public class ConnectTimeoutException extends ConnectException {
    private static final long serialVersionUID = 2317065249988317463L;

    public ConnectTimeoutException(String msg) {
        super(msg);
    }

    public ConnectTimeoutException() {
    }
}
