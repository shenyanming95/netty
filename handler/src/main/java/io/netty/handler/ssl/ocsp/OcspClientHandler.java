package io.netty.handler.ssl.ocsp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.UnstableApi;

import javax.net.ssl.SSLHandshakeException;

/**
 * A handler for SSL clients to handle and act upon stapled OCSP responses.
 *
 * @see ReferenceCountedOpenSslContext#enableOcsp()
 * @see ReferenceCountedOpenSslEngine#getOcspResponse()
 */
@UnstableApi
public abstract class OcspClientHandler extends ChannelInboundHandlerAdapter {

    private final ReferenceCountedOpenSslEngine engine;

    protected OcspClientHandler(ReferenceCountedOpenSslEngine engine) {
        this.engine = ObjectUtil.checkNotNull(engine, "engine");
    }

    /**
     * @see ReferenceCountedOpenSslEngine#getOcspResponse()
     */
    protected abstract boolean verify(ChannelHandlerContext ctx, ReferenceCountedOpenSslEngine engine) throws Exception;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof SslHandshakeCompletionEvent) {
            ctx.pipeline().remove(this);

            SslHandshakeCompletionEvent event = (SslHandshakeCompletionEvent) evt;
            if (event.isSuccess() && !verify(ctx, engine)) {
                throw new SSLHandshakeException("Bad OCSP response");
            }
        }

        ctx.fireUserEventTriggered(evt);
    }
}
