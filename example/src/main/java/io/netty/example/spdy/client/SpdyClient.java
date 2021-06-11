package io.netty.example.spdy.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * An SPDY client that allows you to send HTTP GET to a SPDY server.
 * <p>
 * This class must be run with the JVM parameter: {@code java -Xbootclasspath/p:<path_to_npn_boot_jar> ...}. The
 * "path_to_npn_boot_jar" is the path on the file system for the NPN Boot Jar file which can be downloaded from Maven at
 * coordinates org.mortbay.jetty.npn:npn-boot. Different versions applies to different OpenJDK versions. See
 * <a href="http://www.eclipse.org/jetty/documentation/current/npn-chapter.html">Jetty docs</a> for more information.
 * <p>
 * You may also use the {@code run-example.sh} script to start the client from the command line:
 * <pre>
 *     ./run-example.sh spdy-client
 * </pre>
 */
public final class SpdyClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8443"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).applicationProtocolConfig(new ApplicationProtocolConfig(Protocol.NPN,
                // NO_ADVERTISE is currently the only mode supported by both OpenSsl and JDK providers.
                SelectorFailureBehavior.NO_ADVERTISE,
                // ACCEPT is currently the only mode supported by both OpenSsl and JDK providers.
                SelectedListenerFailureBehavior.ACCEPT, ApplicationProtocolNames.SPDY_3_1, ApplicationProtocolNames.HTTP_1_1)).build();

        HttpResponseClientHandler httpResponseHandler = new HttpResponseClientHandler();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.remoteAddress(HOST, PORT);
            b.handler(new SpdyClientInitializer(sslCtx, httpResponseHandler));

            // Start the client.
            Channel channel = b.connect().syncUninterruptibly().channel();
            System.out.println("Connected to " + HOST + ':' + PORT);

            // Create a GET request.
            HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "", Unpooled.EMPTY_BUFFER);
            request.headers().set(HttpHeaderNames.HOST, HOST);
            request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

            // Send the GET request.
            channel.writeAndFlush(request).sync();

            // Waits for the complete HTTP response
            httpResponseHandler.queue().take().sync();
            System.out.println("Finished SPDY HTTP GET");

            // Wait until the connection is closed.
            channel.close().syncUninterruptibly();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
