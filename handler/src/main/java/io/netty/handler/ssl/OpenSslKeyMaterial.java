package io.netty.handler.ssl;

import io.netty.util.ReferenceCounted;

import java.security.cert.X509Certificate;

/**
 * Holds references to the native key-material that is used by OpenSSL.
 */
interface OpenSslKeyMaterial extends ReferenceCounted {

    /**
     * Returns the configured {@link X509Certificate}s.
     */
    X509Certificate[] certificateChain();

    /**
     * Returns the pointer to the {@code STACK_OF(X509)} which holds the certificate chain.
     */
    long certificateChainAddress();

    /**
     * Returns the pointer to the {@code EVP_PKEY}.
     */
    long privateKeyAddress();

    @Override
    OpenSslKeyMaterial retain();

    @Override
    OpenSslKeyMaterial retain(int increment);

    @Override
    OpenSslKeyMaterial touch();

    @Override
    OpenSslKeyMaterial touch(Object hint);

    @Override
    boolean release();

    @Override
    boolean release(int decrement);
}
