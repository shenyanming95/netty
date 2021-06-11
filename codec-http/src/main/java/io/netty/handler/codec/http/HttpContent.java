package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;

/**
 * An HTTP chunk which is used for HTTP chunked transfer-encoding.
 * {@link HttpObjectDecoder} generates {@link HttpContent} after
 * {@link HttpMessage} when the content is large or the encoding of the content
 * is 'chunked.  If you prefer not to receive {@link HttpContent} in your handler,
 * place {@link HttpObjectAggregator} after {@link HttpObjectDecoder} in the
 * {@link ChannelPipeline}.
 */
public interface HttpContent extends HttpObject, ByteBufHolder {
    @Override
    HttpContent copy();

    @Override
    HttpContent duplicate();

    @Override
    HttpContent retainedDuplicate();

    @Override
    HttpContent replace(ByteBuf content);

    @Override
    HttpContent retain();

    @Override
    HttpContent retain(int increment);

    @Override
    HttpContent touch();

    @Override
    HttpContent touch(Object hint);
}
