package io.netty.util;

/**
 * Represents a supplier of {@code boolean}-valued results which doesn't throw any checked exceptions.
 */
public interface UncheckedBooleanSupplier extends BooleanSupplier {
    /**
     * A supplier which always returns {@code false} and never throws.
     */
    UncheckedBooleanSupplier FALSE_SUPPLIER = new UncheckedBooleanSupplier() {
        @Override
        public boolean get() {
            return false;
        }
    };
    /**
     * A supplier which always returns {@code true} and never throws.
     */
    UncheckedBooleanSupplier TRUE_SUPPLIER = new UncheckedBooleanSupplier() {
        @Override
        public boolean get() {
            return true;
        }
    };

    /**
     * Gets a boolean value.
     *
     * @return a boolean value.
     */
    @Override
    boolean get();
}
