package io.netty.handler.ssl;

import io.netty.util.ReferenceCounted;
import io.netty.util.internal.UnstableApi;

/**
 * An enumeration of SSL/TLS protocol providers.
 */
public enum SslProvider {
    /**
     * JDK's default implementation.
     */
    JDK,
    /**
     * OpenSSL-based implementation.
     */
    OPENSSL,
    /**
     * OpenSSL-based implementation which does not have finalizers and instead implements {@link ReferenceCounted}.
     */
    @UnstableApi OPENSSL_REFCNT;

    /**
     * Returns {@code true} if the specified {@link SslProvider} supports
     * <a href="https://tools.ietf.org/html/rfc7301#section-6">TLS ALPN Extension</a>, {@code false} otherwise.
     */
    public static boolean isAlpnSupported(final SslProvider provider) {
        switch (provider) {
            case JDK:
                return JdkAlpnApplicationProtocolNegotiator.isAlpnSupported();
            case OPENSSL:
            case OPENSSL_REFCNT:
                return OpenSsl.isAlpnSupported();
            default:
                throw new Error("Unknown SslProvider: " + provider);
        }
    }

    /**
     * Returns {@code true} if the specified {@link SslProvider} supports
     * <a href="https://tools.ietf.org/html/rfc8446">TLS 1.3</a>, {@code false} otherwise.
     */
    public static boolean isTlsv13Supported(final SslProvider provider) {
        switch (provider) {
            case JDK:
                return SslUtils.isTLSv13SupportedByJDK();
            case OPENSSL:
            case OPENSSL_REFCNT:
                return OpenSsl.isTlsv13Supported();
            default:
                throw new Error("Unknown SslProvider: " + provider);
        }
    }

    /**
     * Returns {@code true} if the specified {@link SslProvider} enables
     * <a href="https://tools.ietf.org/html/rfc8446">TLS 1.3</a> by default, {@code false} otherwise.
     */
    static boolean isTlsv13EnabledByDefault(final SslProvider provider) {
        switch (provider) {
            case JDK:
                return SslUtils.isTLSv13EnabledByJDK();
            case OPENSSL:
            case OPENSSL_REFCNT:
                return OpenSsl.isTlsv13Supported();
            default:
                throw new Error("Unknown SslProvider: " + provider);
        }
    }
}
