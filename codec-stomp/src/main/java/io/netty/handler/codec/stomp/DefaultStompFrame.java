package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;

/**
 * Default implementation of {@link StompFrame}.
 */
public class DefaultStompFrame extends DefaultStompHeadersSubframe implements StompFrame {

    private final ByteBuf content;

    public DefaultStompFrame(StompCommand command) {
        this(command, Unpooled.buffer(0));
    }

    public DefaultStompFrame(StompCommand command, ByteBuf content) {
        this(command, content, null);
    }

    DefaultStompFrame(StompCommand command, ByteBuf content, DefaultStompHeaders headers) {
        super(command, headers);
        this.content = ObjectUtil.checkNotNull(content, "content");
    }

    @Override
    public ByteBuf content() {
        return content;
    }

    @Override
    public StompFrame copy() {
        return replace(content.copy());
    }

    @Override
    public StompFrame duplicate() {
        return replace(content.duplicate());
    }

    @Override
    public StompFrame retainedDuplicate() {
        return replace(content.retainedDuplicate());
    }

    @Override
    public StompFrame replace(ByteBuf content) {
        return new DefaultStompFrame(command, content, headers.copy());
    }

    @Override
    public int refCnt() {
        return content.refCnt();
    }

    @Override
    public StompFrame retain() {
        content.retain();
        return this;
    }

    @Override
    public StompFrame retain(int increment) {
        content.retain(increment);
        return this;
    }

    @Override
    public StompFrame touch() {
        content.touch();
        return this;
    }

    @Override
    public StompFrame touch(Object hint) {
        content.touch(hint);
        return this;
    }

    @Override
    public boolean release() {
        return content.release();
    }

    @Override
    public boolean release(int decrement) {
        return content.release(decrement);
    }

    @Override
    public String toString() {
        return "DefaultStompFrame{" + "command=" + command + ", headers=" + headers + ", content=" + content.toString(CharsetUtil.UTF_8) + '}';
    }
}
