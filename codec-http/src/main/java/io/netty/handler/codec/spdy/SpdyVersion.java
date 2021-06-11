package io.netty.handler.codec.spdy;

public enum SpdyVersion {
    SPDY_3_1(3, 1);

    private final int version;
    private final int minorVersion;

    SpdyVersion(int version, int minorVersion) {
        this.version = version;
        this.minorVersion = minorVersion;
    }

    int getVersion() {
        return version;
    }

    int getMinorVersion() {
        return minorVersion;
    }
}
