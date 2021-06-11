package io.netty.example.http2.tiles;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Demonstrates an http server using Netty to display a bunch of images, simulate
 * latency and compare it against the http2 implementation.
 */
public final class HttpServer {

    public static final int PORT = Integer.parseInt(System.getProperty("http-port", "8080"));
    private static final int MAX_CONTENT_LENGTH = 1024 * 100;

    private final EventLoopGroup group;

    public HttpServer(EventLoopGroup eventLoopGroup) {
        group = eventLoopGroup;
    }

    public ChannelFuture start() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        b.option(ChannelOption.SO_BACKLOG, 1024);

        b.group(group).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpRequestDecoder(), new HttpResponseEncoder(), new HttpObjectAggregator(MAX_CONTENT_LENGTH), new Http1RequestHandler());
            }
        });

        Channel ch = b.bind(PORT).sync().channel();
        return ch.closeFuture();
    }
}
