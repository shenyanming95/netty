package io.netty.handler.codec.http2;

import io.netty.channel.ChannelId;

/**
 * ChannelId implementation which is used by our {@link Http2StreamChannel} implementation.
 */
final class Http2StreamChannelId implements ChannelId {
    private static final long serialVersionUID = -6642338822166867585L;

    private final int id;
    private final ChannelId parentId;

    Http2StreamChannelId(ChannelId parentId, int id) {
        this.parentId = parentId;
        this.id = id;
    }

    @Override
    public String asShortText() {
        return parentId.asShortText() + '/' + id;
    }

    @Override
    public String asLongText() {
        return parentId.asLongText() + '/' + id;
    }

    @Override
    public int compareTo(ChannelId o) {
        if (o instanceof Http2StreamChannelId) {
            Http2StreamChannelId otherId = (Http2StreamChannelId) o;
            int res = parentId.compareTo(otherId.parentId);
            if (res == 0) {
                return id - otherId.id;
            } else {
                return res;
            }
        }
        return parentId.compareTo(o);
    }

    @Override
    public int hashCode() {
        return id * 31 + parentId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Http2StreamChannelId)) {
            return false;
        }
        Http2StreamChannelId otherId = (Http2StreamChannelId) obj;
        return id == otherId.id && parentId.equals(otherId.parentId);
    }

    @Override
    public String toString() {
        return asShortText();
    }
}
