package io.netty.handler.codec.http2;

import io.netty.channel.Channel;
import io.netty.util.internal.UnstableApi;

// TODO: Should we have an extra method to "open" the stream and so Channel and take care of sending the
//       Http2HeadersFrame under the hood ?
// TODO: Should we extend SocketChannel and map input and output state to the stream state ?
//
@UnstableApi
public interface Http2StreamChannel extends Channel {

    /**
     * Returns the {@link Http2FrameStream} that belongs to this channel.
     */
    Http2FrameStream stream();
}
