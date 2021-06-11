package io.netty.channel.local;

import io.netty.channel.Channel;
import io.netty.util.internal.ObjectUtil;

import java.net.SocketAddress;

/**
 * An endpoint in the local transport.  Each endpoint is identified by a unique
 * case-insensitive string.
 */
public final class LocalAddress extends SocketAddress implements Comparable<LocalAddress> {

    public static final LocalAddress ANY = new LocalAddress("ANY");
    private static final long serialVersionUID = 4644331421130916435L;
    private final String id;
    private final String strVal;

    /**
     * Creates a new ephemeral port based on the ID of the specified channel.
     * Note that we prepend an upper-case character so that it never conflicts with
     * the addresses created by a user, which are always lower-cased on construction time.
     */
    LocalAddress(Channel channel) {
        StringBuilder buf = new StringBuilder(16);
        buf.append("local:E");
        buf.append(Long.toHexString(channel.hashCode() & 0xFFFFFFFFL | 0x100000000L));
        buf.setCharAt(7, ':');
        id = buf.substring(6);
        strVal = buf.toString();
    }

    /**
     * Creates a new instance with the specified ID.
     */
    public LocalAddress(String id) {
        ObjectUtil.checkNotNull(id, "id");
        id = id.trim().toLowerCase();
        if (id.isEmpty()) {
            throw new IllegalArgumentException("empty id");
        }
        this.id = id;
        strVal = "local:" + id;
    }

    /**
     * Returns the ID of this address.
     */
    public String id() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocalAddress)) {
            return false;
        }

        return id.equals(((LocalAddress) o).id);
    }

    @Override
    public int compareTo(LocalAddress o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return strVal;
    }
}
