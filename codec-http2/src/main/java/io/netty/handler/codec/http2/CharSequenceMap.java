package io.netty.handler.codec.http2;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.UnsupportedValueConverter;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.internal.UnstableApi;

import static io.netty.util.AsciiString.CASE_INSENSITIVE_HASHER;
import static io.netty.util.AsciiString.CASE_SENSITIVE_HASHER;

/**
 * Internal use only!
 */
@UnstableApi
public final class CharSequenceMap<V> extends DefaultHeaders<CharSequence, V, CharSequenceMap<V>> {
    public CharSequenceMap() {
        this(true);
    }

    public CharSequenceMap(boolean caseSensitive) {
        this(caseSensitive, UnsupportedValueConverter.<V>instance());
    }

    public CharSequenceMap(boolean caseSensitive, ValueConverter<V> valueConverter) {
        super(caseSensitive ? CASE_SENSITIVE_HASHER : CASE_INSENSITIVE_HASHER, valueConverter);
    }

    @SuppressWarnings("unchecked")
    public CharSequenceMap(boolean caseSensitive, ValueConverter<V> valueConverter, int arraySizeHint) {
        super(caseSensitive ? CASE_SENSITIVE_HASHER : CASE_INSENSITIVE_HASHER, valueConverter, NameValidator.NOT_NULL, arraySizeHint);
    }
}
