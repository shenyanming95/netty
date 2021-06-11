package io.netty.handler.codec.memcache.binary;

import io.netty.util.internal.UnstableApi;

/**
 * Contains all possible status values a {@link BinaryMemcacheResponse} can return.
 */
@UnstableApi
public final class BinaryMemcacheResponseStatus {

    public static final short SUCCESS = 0x00;
    public static final short KEY_ENOENT = 0x01;
    public static final short KEY_EEXISTS = 0x02;
    public static final short E2BIG = 0x03;
    public static final short EINVA = 0x04;
    public static final short NOT_STORED = 0x05;
    public static final short DELTA_BADVAL = 0x06;
    public static final short AUTH_ERROR = 0x20;
    public static final short AUTH_CONTINUE = 0x21;
    public static final short UNKNOWN_COMMAND = 0x81;
    public static final short ENOMEM = 0x82;

    private BinaryMemcacheResponseStatus() {
        // disallow construction
    }
}
