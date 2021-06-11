package io.netty.handler.ssl;

import javax.net.ssl.SSLHandshakeException;

/**
 * {@link SSLHandshakeException} that is used when a handshake failed due a configured timeout.
 */
public final class SslHandshakeTimeoutException extends SSLHandshakeException {

    SslHandshakeTimeoutException(String reason) {
        super(reason);
    }
}
