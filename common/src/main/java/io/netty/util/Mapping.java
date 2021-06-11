package io.netty.util;

/**
 * Maintains the mapping from the objects of one type to the objects of the other type.
 */
public interface Mapping<IN, OUT> {

    /**
     * Returns mapped value of the specified input.
     */
    OUT map(IN input);
}
