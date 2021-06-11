package io.netty.handler.codec.http2;

import io.netty.util.internal.UnstableApi;

@UnstableApi
public final class Http2FrameStreamEvent {

    private final Http2FrameStream stream;
    private final Type type;

    private Http2FrameStreamEvent(Http2FrameStream stream, Type type) {
        this.stream = stream;
        this.type = type;
    }

    static Http2FrameStreamEvent stateChanged(Http2FrameStream stream) {
        return new Http2FrameStreamEvent(stream, Type.State);
    }

    static Http2FrameStreamEvent writabilityChanged(Http2FrameStream stream) {
        return new Http2FrameStreamEvent(stream, Type.Writability);
    }

    public Http2FrameStream stream() {
        return stream;
    }

    public Type type() {
        return type;
    }

    enum Type {
        State, Writability
    }
}
