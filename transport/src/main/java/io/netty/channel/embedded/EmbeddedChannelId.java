package io.netty.channel.embedded;

import io.netty.channel.ChannelId;

/**
 * A dummy {@link ChannelId} implementation.
 */
final class EmbeddedChannelId implements ChannelId {

    static final ChannelId INSTANCE = new EmbeddedChannelId();
    private static final long serialVersionUID = -251711922203466130L;

    private EmbeddedChannelId() {
    }

    @Override
    public String asShortText() {
        return toString();
    }

    @Override
    public String asLongText() {
        return toString();
    }

    @Override
    public int compareTo(final ChannelId o) {
        if (o instanceof EmbeddedChannelId) {
            return 0;
        }

        return asLongText().compareTo(o.asLongText());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmbeddedChannelId;
    }

    @Override
    public String toString() {
        return "embedded";
    }
}
