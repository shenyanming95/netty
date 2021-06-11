package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/**
 * The default {@link SpdyPingFrame} implementation.
 */
public class DefaultSpdyPingFrame implements SpdyPingFrame {

    private int id;

    /**
     * Creates a new instance.
     *
     * @param id the unique ID of this frame
     */
    public DefaultSpdyPingFrame(int id) {
        setId(id);
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public SpdyPingFrame setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(StringUtil.simpleClassName(this)).append(StringUtil.NEWLINE).append("--> ID = ").append(id()).toString();
    }
}
