package io.netty.handler.codec.stomp;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.ObjectUtil;

/**
 * Default implementation of {@link StompHeadersSubframe}.
 */
public class DefaultStompHeadersSubframe implements StompHeadersSubframe {

    protected final StompCommand command;
    protected final DefaultStompHeaders headers;
    protected DecoderResult decoderResult = DecoderResult.SUCCESS;

    public DefaultStompHeadersSubframe(StompCommand command) {
        this(command, null);
    }

    DefaultStompHeadersSubframe(StompCommand command, DefaultStompHeaders headers) {
        this.command = ObjectUtil.checkNotNull(command, "command");
        this.headers = headers == null ? new DefaultStompHeaders() : headers;
    }

    @Override
    public StompCommand command() {
        return command;
    }

    @Override
    public StompHeaders headers() {
        return headers;
    }

    @Override
    public DecoderResult decoderResult() {
        return decoderResult;
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        this.decoderResult = decoderResult;
    }

    @Override
    public String toString() {
        return "StompFrame{" + "command=" + command + ", headers=" + headers + '}';
    }
}
