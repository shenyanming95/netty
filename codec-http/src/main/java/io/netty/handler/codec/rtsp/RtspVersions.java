package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.internal.ObjectUtil;

/**
 * The version of RTSP.
 */
public final class RtspVersions {

    /**
     * RTSP/1.0
     */
    public static final HttpVersion RTSP_1_0 = new HttpVersion("RTSP", 1, 0, true);

    private RtspVersions() {
    }

    /**
     * Returns an existing or new {@link HttpVersion} instance which matches to
     * the specified RTSP version string.  If the specified {@code text} is
     * equal to {@code "RTSP/1.0"}, {@link #RTSP_1_0} will be returned.
     * Otherwise, a new {@link HttpVersion} instance will be returned.
     */
    public static HttpVersion valueOf(String text) {
        ObjectUtil.checkNotNull(text, "text");

        text = text.trim().toUpperCase();
        if ("RTSP/1.0".equals(text)) {
            return RTSP_1_0;
        }

        return new HttpVersion(text, true);
    }
}
