package io.netty.handler.ssl;

import io.netty.util.internal.PlatformDependent;

import javax.net.ssl.SSLEngine;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Contains methods that can be used to detect if conscrypt is usable.
 */
final class Conscrypt {
    // This class exists to avoid loading other conscrypt related classes using features only available in JDK8+,
    // because we need to maintain JDK6+ runtime compatibility.
    private static final Method IS_CONSCRYPT_SSLENGINE;

    static {
        Method isConscryptSSLEngine = null;

        if ((PlatformDependent.javaVersion() >= 8 &&
                // Only works on Java14 and earlier for now
                // See https://github.com/google/conscrypt/issues/838
                PlatformDependent.javaVersion() < 15) || PlatformDependent.isAndroid()) {
            try {
                Class<?> providerClass = Class.forName("org.conscrypt.OpenSSLProvider", true, PlatformDependent.getClassLoader(ConscryptAlpnSslEngine.class));
                providerClass.newInstance();

                Class<?> conscryptClass = Class.forName("org.conscrypt.Conscrypt", true, PlatformDependent.getClassLoader(ConscryptAlpnSslEngine.class));
                isConscryptSSLEngine = conscryptClass.getMethod("isConscrypt", SSLEngine.class);
            } catch (Throwable ignore) {
                // ignore
            }
        }
        IS_CONSCRYPT_SSLENGINE = isConscryptSSLEngine;
    }

    private Conscrypt() {
    }

    /**
     * Indicates whether or not conscrypt is available on the current system.
     */
    static boolean isAvailable() {
        return IS_CONSCRYPT_SSLENGINE != null;
    }

    /**
     * Returns {@code true} if the passed in {@link SSLEngine} is handled by Conscrypt, {@code false} otherwise.
     */
    static boolean isEngineSupported(SSLEngine engine) {
        try {
            return IS_CONSCRYPT_SSLENGINE != null && (Boolean) IS_CONSCRYPT_SSLENGINE.invoke(null, engine);
        } catch (IllegalAccessException ignore) {
            return false;
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
