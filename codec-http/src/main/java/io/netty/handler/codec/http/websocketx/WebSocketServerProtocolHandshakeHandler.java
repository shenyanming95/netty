/*
 * Copyright 2019 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.ServerHandshakeStateEvent;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * Handles the HTTP handshake (the HTTP Upgrade request) for {@link WebSocketServerProtocolHandler}.
 */
class WebSocketServerProtocolHandshakeHandler extends ChannelInboundHandlerAdapter {

    private final WebSocketServerProtocolConfig serverConfig;
    private ChannelHandlerContext ctx;
    private ChannelPromise handshakePromise;

    WebSocketServerProtocolHandshakeHandler(WebSocketServerProtocolConfig serverConfig) {
        this.serverConfig = checkNotNull(serverConfig, "serverConfig");
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            // SSL in use so use Secure WebSockets
            protocol = "wss";
        }
        String host = req.headers().get(HttpHeaderNames.HOST);
        return protocol + "://" + host + path;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        handshakePromise = ctx.newPromise();
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        final FullHttpRequest req = (FullHttpRequest) msg;
        if (isNotWebSocketPath(req)) {
            ctx.fireChannelRead(msg);
            return;
        }

        try {
            if (!GET.equals(req.method())) {
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN, ctx.alloc().buffer(0)));
                return;
            }

            final WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(ctx.pipeline(), req, serverConfig.websocketPath()), serverConfig.subprotocols(), serverConfig.decoderConfig());
            final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            final ChannelPromise localHandshakePromise = handshakePromise;
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // Ensure we set the handshaker and replace this handler before we
                // trigger the actual handshake. Otherwise we may receive websocket bytes in this handler
                // before we had a chance to replace it.
                //
                // See https://github.com/netty/netty/issues/9471.
                WebSocketServerProtocolHandler.setHandshaker(ctx.channel(), handshaker);
                ctx.pipeline().remove(this);

                final ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
                handshakeFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (!future.isSuccess()) {
                            localHandshakePromise.tryFailure(future.cause());
                            ctx.fireExceptionCaught(future.cause());
                        } else {
                            localHandshakePromise.trySuccess();
                            // Kept for compatibility
                            ctx.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                            ctx.fireUserEventTriggered(new WebSocketServerProtocolHandler.HandshakeComplete(req.uri(), req.headers(), handshaker.selectedSubprotocol()));
                        }
                    }
                });
                applyHandshakeTimeout();
            }
        } finally {
            req.release();
        }
    }

    private boolean isNotWebSocketPath(FullHttpRequest req) {
        String websocketPath = serverConfig.websocketPath();
        return serverConfig.checkStartsWith() ? !req.uri().startsWith(websocketPath) : !req.uri().equals(websocketPath);
    }

    private void applyHandshakeTimeout() {
        final ChannelPromise localHandshakePromise = handshakePromise;
        final long handshakeTimeoutMillis = serverConfig.handshakeTimeoutMillis();
        if (handshakeTimeoutMillis <= 0 || localHandshakePromise.isDone()) {
            return;
        }

        final Future<?> timeoutFuture = ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                if (!localHandshakePromise.isDone() && localHandshakePromise.tryFailure(new WebSocketHandshakeException("handshake timed out"))) {
                    ctx.flush().fireUserEventTriggered(ServerHandshakeStateEvent.HANDSHAKE_TIMEOUT).close();
                }
            }
        }, handshakeTimeoutMillis, TimeUnit.MILLISECONDS);

        // Cancel the handshake timeout when handshake is finished.
        localHandshakePromise.addListener(new FutureListener<Void>() {
            @Override
            public void operationComplete(Future<Void> f) {
                timeoutFuture.cancel(false);
            }
        });
    }
}
