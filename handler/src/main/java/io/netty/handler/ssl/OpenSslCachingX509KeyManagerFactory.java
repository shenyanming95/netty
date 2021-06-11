package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;

import javax.net.ssl.*;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * Wraps another {@link KeyManagerFactory} and caches its chains / certs for an alias for better performance when using
 * {@link SslProvider#OPENSSL} or {@link SslProvider#OPENSSL_REFCNT}.
 * <p>
 * Because of the caching its important that the wrapped {@link KeyManagerFactory}s {@link X509KeyManager}s always
 * return the same {@link X509Certificate} chain and {@link PrivateKey} for the same alias.
 */
public final class OpenSslCachingX509KeyManagerFactory extends KeyManagerFactory {

    private final int maxCachedEntries;

    public OpenSslCachingX509KeyManagerFactory(final KeyManagerFactory factory) {
        this(factory, 1024);
    }

    public OpenSslCachingX509KeyManagerFactory(final KeyManagerFactory factory, int maxCachedEntries) {
        super(new KeyManagerFactorySpi() {
            @Override
            protected void engineInit(KeyStore keyStore, char[] chars) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
                factory.init(keyStore, chars);
            }

            @Override
            protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
                factory.init(managerFactoryParameters);
            }

            @Override
            protected KeyManager[] engineGetKeyManagers() {
                return factory.getKeyManagers();
            }
        }, factory.getProvider(), factory.getAlgorithm());
        this.maxCachedEntries = ObjectUtil.checkPositive(maxCachedEntries, "maxCachedEntries");
    }

    OpenSslKeyMaterialProvider newProvider(String password) {
        X509KeyManager keyManager = ReferenceCountedOpenSslContext.chooseX509KeyManager(getKeyManagers());
        if ("sun.security.ssl.X509KeyManagerImpl".equals(keyManager.getClass().getName())) {
            // Don't do caching if X509KeyManagerImpl is used as the returned aliases are not stable and will change
            // between invocations.
            return new OpenSslKeyMaterialProvider(keyManager, password);
        }
        return new OpenSslCachingKeyMaterialProvider(ReferenceCountedOpenSslContext.chooseX509KeyManager(getKeyManagers()), password, maxCachedEntries);
    }
}
