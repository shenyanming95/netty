package io.netty.example.udt.echo.message;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * UDT Message Flow Server
 * <p>
 * Echoes back any received data from a client.
 */
public final class MsgEchoServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        final ThreadFactory acceptFactory = new DefaultThreadFactory("accept");
        final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
        final NioEventLoopGroup acceptGroup = new NioEventLoopGroup(1, acceptFactory, NioUdtProvider.MESSAGE_PROVIDER);
        final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1, connectFactory, NioUdtProvider.MESSAGE_PROVIDER);

        // Configure the server.
        try {
            final ServerBootstrap boot = new ServerBootstrap();
            boot.group(acceptGroup, connectGroup).channelFactory(NioUdtProvider.MESSAGE_ACCEPTOR).option(ChannelOption.SO_BACKLOG, 10).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<UdtChannel>() {
                @Override
                public void initChannel(final UdtChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO), new MsgEchoServerHandler());
                }
            });
            // Start the server.
            final ChannelFuture future = boot.bind(PORT).sync();
            // Wait until the server socket is closed.
            future.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            acceptGroup.shutdownGracefully();
            connectGroup.shutdownGracefully();
        }
    }
}
