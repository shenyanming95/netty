package io.netty.handler.ssl;

import io.netty.util.internal.EmptyArrays;

import java.util.Arrays;

/**
 * Represent the session ID used by an {@link OpenSslSession}.
 */
final class OpenSslSessionId {

    static final OpenSslSessionId NULL_ID = new OpenSslSessionId(EmptyArrays.EMPTY_BYTES);
    private final byte[] id;
    private final int hashCode;

    OpenSslSessionId(byte[] id) {
        // We take ownership if the byte[] and so there is no need to clone it.
        this.id = id;
        // cache the hashCode as the byte[] array will never change
        this.hashCode = Arrays.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpenSslSessionId)) {
            return false;
        }

        return Arrays.equals(id, ((OpenSslSessionId) o).id);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    byte[] cloneBytes() {
        return id.clone();
    }
}
