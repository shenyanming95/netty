package io.netty.example.spdy.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

/**
 * Sets up the Netty pipeline
 */
public class SpdyServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public SpdyServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(sslCtx.newHandler(ch.alloc()));
        // Negotiates with the browser if SPDY or HTTP is going to be used
        p.addLast(new SpdyOrHttpHandler());
    }
}
