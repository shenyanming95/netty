package io.netty.handler.codec.rtsp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

import static io.netty.handler.codec.http.HttpConstants.*;

/**
 * Encodes an RTSP message represented in {@link HttpMessage} or an {@link HttpContent} into
 * a {@link ByteBuf}.
 */
public class RtspEncoder extends HttpObjectEncoder<HttpMessage> {
    private static final int CRLF_SHORT = (CR << 8) | LF;

    @Override
    public boolean acceptOutboundMessage(final Object msg) throws Exception {
        return super.acceptOutboundMessage(msg) && ((msg instanceof HttpRequest) || (msg instanceof HttpResponse));
    }

    @Override
    protected void encodeInitialLine(final ByteBuf buf, final HttpMessage message) throws Exception {
        if (message instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) message;
            ByteBufUtil.copy(request.method().asciiName(), buf);
            buf.writeByte(SP);
            buf.writeCharSequence(request.uri(), CharsetUtil.UTF_8);
            buf.writeByte(SP);
            buf.writeCharSequence(request.protocolVersion().toString(), CharsetUtil.US_ASCII);
            ByteBufUtil.writeShortBE(buf, CRLF_SHORT);
        } else if (message instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) message;
            buf.writeCharSequence(response.protocolVersion().toString(), CharsetUtil.US_ASCII);
            buf.writeByte(SP);
            ByteBufUtil.copy(response.status().codeAsText(), buf);
            buf.writeByte(SP);
            buf.writeCharSequence(response.status().reasonPhrase(), CharsetUtil.US_ASCII);
            ByteBufUtil.writeShortBE(buf, CRLF_SHORT);
        } else {
            throw new UnsupportedMessageTypeException("Unsupported type " + StringUtil.simpleClassName(message));
        }
    }
}
