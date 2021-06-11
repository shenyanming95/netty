package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

/**
 * Combination of a {@link HttpResponse} and {@link FullHttpMessage}.
 * So it represent a <i>complete</i> http response.
 */
public interface FullHttpResponse extends HttpResponse, FullHttpMessage {
    @Override
    FullHttpResponse copy();

    @Override
    FullHttpResponse duplicate();

    @Override
    FullHttpResponse retainedDuplicate();

    @Override
    FullHttpResponse replace(ByteBuf content);

    @Override
    FullHttpResponse retain(int increment);

    @Override
    FullHttpResponse retain();

    @Override
    FullHttpResponse touch();

    @Override
    FullHttpResponse touch(Object hint);

    @Override
    FullHttpResponse setProtocolVersion(HttpVersion version);

    @Override
    FullHttpResponse setStatus(HttpResponseStatus status);
}
