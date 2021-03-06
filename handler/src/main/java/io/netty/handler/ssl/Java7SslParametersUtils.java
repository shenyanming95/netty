package io.netty.handler.ssl;

import io.netty.util.internal.SuppressJava6Requirement;

import javax.net.ssl.SSLParameters;
import java.security.AlgorithmConstraints;

final class Java7SslParametersUtils {

    private Java7SslParametersUtils() {
        // Utility
    }

    /**
     * Utility method that is used by {@link OpenSslEngine} and so allow use not not have any reference to
     * {@link AlgorithmConstraints} in the code. This helps us to not get into trouble when using it in java
     * version < 7 and especially when using on android.
     */
    @SuppressJava6Requirement(reason = "Usage guarded by java version check")
    static void setAlgorithmConstraints(SSLParameters sslParameters, Object algorithmConstraints) {
        sslParameters.setAlgorithmConstraints((AlgorithmConstraints) algorithmConstraints);
    }
}
