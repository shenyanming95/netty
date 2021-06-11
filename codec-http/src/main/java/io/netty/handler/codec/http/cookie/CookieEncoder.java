package io.netty.handler.codec.http.cookie;

import static io.netty.handler.codec.http.cookie.CookieUtil.*;

/**
 * Parent of Client and Server side cookie encoders
 */
public abstract class CookieEncoder {

    protected final boolean strict;

    protected CookieEncoder(boolean strict) {
        this.strict = strict;
    }

    protected void validateCookie(String name, String value) {
        if (strict) {
            int pos;

            if ((pos = firstInvalidCookieNameOctet(name)) >= 0) {
                throw new IllegalArgumentException("Cookie name contains an invalid char: " + name.charAt(pos));
            }

            CharSequence unwrappedValue = unwrapValue(value);
            if (unwrappedValue == null) {
                throw new IllegalArgumentException("Cookie value wrapping quotes are not balanced: " + value);
            }

            if ((pos = firstInvalidCookieValueOctet(unwrappedValue)) >= 0) {
                throw new IllegalArgumentException("Cookie value contains an invalid char: " + unwrappedValue.charAt(pos));
            }
        }
    }
}
