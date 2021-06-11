package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

/**
 * Combine the {@link HttpRequest} and {@link FullHttpMessage}, so the request is a <i>complete</i> HTTP
 * request.
 */
public interface FullHttpRequest extends HttpRequest, FullHttpMessage {
    @Override
    FullHttpRequest copy();

    @Override
    FullHttpRequest duplicate();

    @Override
    FullHttpRequest retainedDuplicate();

    @Override
    FullHttpRequest replace(ByteBuf content);

    @Override
    FullHttpRequest retain(int increment);

    @Override
    FullHttpRequest retain();

    @Override
    FullHttpRequest touch();

    @Override
    FullHttpRequest touch(Object hint);

    @Override
    FullHttpRequest setProtocolVersion(HttpVersion version);

    @Override
    FullHttpRequest setMethod(HttpMethod method);

    @Override
    FullHttpRequest setUri(String uri);
}
