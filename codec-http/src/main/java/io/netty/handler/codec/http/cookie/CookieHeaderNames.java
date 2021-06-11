package io.netty.handler.codec.http.cookie;

public final class CookieHeaderNames {
    public static final String PATH = "Path";

    public static final String EXPIRES = "Expires";

    public static final String MAX_AGE = "Max-Age";

    public static final String DOMAIN = "Domain";

    public static final String SECURE = "Secure";

    public static final String HTTPONLY = "HTTPOnly";

    public static final String SAMESITE = "SameSite";

    private CookieHeaderNames() {
        // Unused.
    }

    /**
     * Possible values for the SameSite attribute.
     * See <a href="https://tools.ietf.org/html/draft-ietf-httpbis-rfc6265bis-05">changes to RFC6265bis</a>
     */
    public enum SameSite {
        Lax, Strict, None;

        /**
         * Return the enum value corresponding to the passed in same-site-flag, using a case insensitive comparison.
         *
         * @param name value for the SameSite Attribute
         * @return enum value for the provided name or null
         */
        static SameSite of(String name) {
            if (name != null) {
                for (SameSite each : SameSite.class.getEnumConstants()) {
                    if (each.name().equalsIgnoreCase(name)) {
                        return each;
                    }
                }
            }
            return null;
        }
    }
}
