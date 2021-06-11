package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Web Socket frame containing binary data.
 */
public class BinaryWebSocketFrame extends WebSocketFrame {

    /**
     * Creates a new empty binary frame.
     */
    public BinaryWebSocketFrame() {
        super(Unpooled.buffer(0));
    }

    /**
     * Creates a new binary frame with the specified binary data. The final fragment flag is set to true.
     *
     * @param binaryData the content of the frame.
     */
    public BinaryWebSocketFrame(ByteBuf binaryData) {
        super(binaryData);
    }

    /**
     * Creates a new binary frame with the specified binary data and the final fragment flag.
     *
     * @param finalFragment flag indicating if this frame is the final fragment
     * @param rsv           reserved bits used for protocol extensions
     * @param binaryData    the content of the frame.
     */
    public BinaryWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }

    @Override
    public BinaryWebSocketFrame copy() {
        return (BinaryWebSocketFrame) super.copy();
    }

    @Override
    public BinaryWebSocketFrame duplicate() {
        return (BinaryWebSocketFrame) super.duplicate();
    }

    @Override
    public BinaryWebSocketFrame retainedDuplicate() {
        return (BinaryWebSocketFrame) super.retainedDuplicate();
    }

    @Override
    public BinaryWebSocketFrame replace(ByteBuf content) {
        return new BinaryWebSocketFrame(isFinalFragment(), rsv(), content);
    }

    @Override
    public BinaryWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryWebSocketFrame retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public BinaryWebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override
    public BinaryWebSocketFrame touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
