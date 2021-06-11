package io.netty.util;

/**
 * Builder for immutable {@link DomainNameMapping} instances.
 *
 * @param <V> concrete type of value objects
 * @deprecated Use {@link DomainWildcardMappingBuilder} instead.
 */
@Deprecated
public final class DomainMappingBuilder<V> {

    private final DomainNameMappingBuilder<V> builder;

    /**
     * Constructor with default initial capacity of the map holding the mappings
     *
     * @param defaultValue the default value for {@link DomainNameMapping#map(String)} to return
     *                     when nothing matches the input
     */
    public DomainMappingBuilder(V defaultValue) {
        builder = new DomainNameMappingBuilder<V>(defaultValue);
    }

    /**
     * Constructor with initial capacity of the map holding the mappings
     *
     * @param initialCapacity initial capacity for the internal map
     * @param defaultValue    the default value for {@link DomainNameMapping#map(String)} to return
     *                        when nothing matches the input
     */
    public DomainMappingBuilder(int initialCapacity, V defaultValue) {
        builder = new DomainNameMappingBuilder<V>(initialCapacity, defaultValue);
    }

    /**
     * Adds a mapping that maps the specified (optionally wildcard) host name to the specified output value.
     * Null values are forbidden for both hostnames and values.
     * <p>
     * <a href="http://en.wikipedia.org/wiki/Wildcard_DNS_record">DNS wildcard</a> is supported as hostname.
     * For example, you can use {@code *.netty.io} to match {@code netty.io} and {@code downloads.netty.io}.
     * </p>
     *
     * @param hostname the host name (optionally wildcard)
     * @param output   the output value that will be returned by {@link DomainNameMapping#map(String)}
     *                 when the specified host name matches the specified input host name
     */
    public DomainMappingBuilder<V> add(String hostname, V output) {
        builder.add(hostname, output);
        return this;
    }

    /**
     * Creates a new instance of immutable {@link DomainNameMapping}
     * Attempts to add new mappings to the result object will cause {@link UnsupportedOperationException} to be thrown
     *
     * @return new {@link DomainNameMapping} instance
     */
    public DomainNameMapping<V> build() {
        return builder.build();
    }
}
