package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Web Socket frame containing binary data.
 */
public class PongWebSocketFrame extends WebSocketFrame {

    /**
     * Creates a new empty pong frame.
     */
    public PongWebSocketFrame() {
        super(Unpooled.buffer(0));
    }

    /**
     * Creates a new pong frame with the specified binary data.
     *
     * @param binaryData the content of the frame.
     */
    public PongWebSocketFrame(ByteBuf binaryData) {
        super(binaryData);
    }

    /**
     * Creates a new pong frame with the specified binary data
     *
     * @param finalFragment flag indicating if this frame is the final fragment
     * @param rsv           reserved bits used for protocol extensions
     * @param binaryData    the content of the frame.
     */
    public PongWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }

    @Override
    public PongWebSocketFrame copy() {
        return (PongWebSocketFrame) super.copy();
    }

    @Override
    public PongWebSocketFrame duplicate() {
        return (PongWebSocketFrame) super.duplicate();
    }

    @Override
    public PongWebSocketFrame retainedDuplicate() {
        return (PongWebSocketFrame) super.retainedDuplicate();
    }

    @Override
    public PongWebSocketFrame replace(ByteBuf content) {
        return new PongWebSocketFrame(isFinalFragment(), rsv(), content);
    }

    @Override
    public PongWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public PongWebSocketFrame retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public PongWebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override
    public PongWebSocketFrame touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
