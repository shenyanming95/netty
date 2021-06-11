package io.netty.handler.ssl;

import io.netty.util.ReferenceCounted;

import javax.net.ssl.SSLSession;
import java.security.cert.Certificate;

/**
 * {@link SSLSession} that is specific to our native implementation and {@link ReferenceCounted} to track native
 * resources.
 */
interface OpenSslSession extends SSLSession, ReferenceCounted {

    /**
     * Return the {@link OpenSslSessionId} that can be used to identify this session.
     */
    OpenSslSessionId sessionId();

    /**
     * Returns {@code true} if this is a {@code NULL} session and so should be replaced once the
     * handshake is in progress / finished, {@code false} otherwise.
     */
    boolean isNullSession();

    /**
     * Returns the native address (pointer) to {@code SSL_SESSION*} if any. Otherwise returns {@code -1}.
     */
    long nativeAddr();

    /**
     * Set the local certificate chain that is used. It is not expected that this array will be changed at all
     * and so its ok to not copy the array.
     */
    void setLocalCertificate(Certificate[] localCertificate);

    @Override
    OpenSslSessionContext getSessionContext();

    /**
     * Expand (or increase) the value returned by {@link #getApplicationBufferSize()} if necessary.
     * <p>
     * This is only called in a synchronized block, so no need to use atomic operations.
     *
     * @param packetLengthDataOnly The packet size which exceeds the current {@link #getApplicationBufferSize()}.
     */
    void tryExpandApplicationBufferSize(int packetLengthDataOnly);

    /**
     * Set the buffer size that is needed as a minimum to encrypt data.
     */
    void setPacketBufferSize(int packetBufferSize);

    /**
     * Update the last accessed time to the current {@link System#currentTimeMillis()}.
     */
    void updateLastAccessedTime();

    @Override
    OpenSslSession retain();

    @Override
    OpenSslSession retain(int increment);

    @Override
    OpenSslSession touch();

    @Override
    OpenSslSession touch(Object hint);
}
