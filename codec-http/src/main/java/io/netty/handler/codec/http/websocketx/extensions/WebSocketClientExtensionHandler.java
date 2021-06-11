package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.internal.ObjectUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This handler negotiates and initializes the WebSocket Extensions.
 * <p>
 * This implementation negotiates the extension with the server in a defined order,
 * ensures that the successfully negotiated extensions are consistent between them,
 * and initializes the channel pipeline with the extension decoder and encoder.
 * <p>
 * Find a basic implementation for compression extensions at
 * <tt>io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler</tt>.
 */
public class WebSocketClientExtensionHandler extends ChannelDuplexHandler {

    private final List<WebSocketClientExtensionHandshaker> extensionHandshakers;

    /**
     * Constructor
     *
     * @param extensionHandshakers The extension handshaker in priority order. A handshaker could be repeated many times
     *                             with fallback configuration.
     */
    public WebSocketClientExtensionHandler(WebSocketClientExtensionHandshaker... extensionHandshakers) {
        ObjectUtil.checkNotNull(extensionHandshakers, "extensionHandshakers");
        if (extensionHandshakers.length == 0) {
            throw new IllegalArgumentException("extensionHandshakers must contains at least one handshaker");
        }
        this.extensionHandshakers = Arrays.asList(extensionHandshakers);
    }

    @Override
    public void write(final ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof HttpRequest && WebSocketExtensionUtil.isWebsocketUpgrade(((HttpRequest) msg).headers())) {
            HttpRequest request = (HttpRequest) msg;
            String headerValue = request.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);

            for (WebSocketClientExtensionHandshaker extensionHandshaker : extensionHandshakers) {
                WebSocketExtensionData extensionData = extensionHandshaker.newRequestData();
                headerValue = WebSocketExtensionUtil.appendExtension(headerValue, extensionData.name(), extensionData.parameters());
            }

            request.headers().set(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, headerValue);
        }

        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            if (WebSocketExtensionUtil.isWebsocketUpgrade(response.headers())) {
                String extensionsHeader = response.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);

                if (extensionsHeader != null) {
                    List<WebSocketExtensionData> extensions = WebSocketExtensionUtil.extractExtensions(extensionsHeader);
                    List<WebSocketClientExtension> validExtensions = new ArrayList<WebSocketClientExtension>(extensions.size());
                    int rsv = 0;

                    for (WebSocketExtensionData extensionData : extensions) {
                        Iterator<WebSocketClientExtensionHandshaker> extensionHandshakersIterator = extensionHandshakers.iterator();
                        WebSocketClientExtension validExtension = null;

                        while (validExtension == null && extensionHandshakersIterator.hasNext()) {
                            WebSocketClientExtensionHandshaker extensionHandshaker = extensionHandshakersIterator.next();
                            validExtension = extensionHandshaker.handshakeExtension(extensionData);
                        }

                        if (validExtension != null && ((validExtension.rsv() & rsv) == 0)) {
                            rsv = rsv | validExtension.rsv();
                            validExtensions.add(validExtension);
                        } else {
                            throw new CodecException("invalid WebSocket Extension handshake for \"" + extensionsHeader + '"');
                        }
                    }

                    for (WebSocketClientExtension validExtension : validExtensions) {
                        WebSocketExtensionDecoder decoder = validExtension.newExtensionDecoder();
                        WebSocketExtensionEncoder encoder = validExtension.newExtensionEncoder();
                        ctx.pipeline().addAfter(ctx.name(), decoder.getClass().getName(), decoder);
                        ctx.pipeline().addAfter(ctx.name(), encoder.getClass().getName(), encoder);
                    }
                }

                ctx.pipeline().remove(ctx.name());
            }
        }

        super.channelRead(ctx, msg);
    }
}

