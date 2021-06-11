package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class will not do any filtering of ciphers suites.
 */
public final class IdentityCipherSuiteFilter implements CipherSuiteFilter {

    /**
     * Defaults to default ciphers when provided ciphers are null
     */
    public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter(true);

    /**
     * Defaults to supported ciphers when provided ciphers are null
     */
    public static final IdentityCipherSuiteFilter INSTANCE_DEFAULTING_TO_SUPPORTED_CIPHERS = new IdentityCipherSuiteFilter(false);

    private final boolean defaultToDefaultCiphers;

    private IdentityCipherSuiteFilter(boolean defaultToDefaultCiphers) {
        this.defaultToDefaultCiphers = defaultToDefaultCiphers;
    }

    @Override
    public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
        if (ciphers == null) {
            return defaultToDefaultCiphers ? defaultCiphers.toArray(new String[0]) : supportedCiphers.toArray(new String[0]);
        } else {
            List<String> newCiphers = new ArrayList<String>(supportedCiphers.size());
            for (String c : ciphers) {
                if (c == null) {
                    break;
                }
                newCiphers.add(c);
            }
            return newCiphers.toArray(new String[0]);
        }
    }
}
