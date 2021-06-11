package io.netty.handler.ssl;

import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;

import java.util.concurrent.locks.Lock;


/**
 * {@link OpenSslSessionContext} implementation which offers extra methods which are only useful for the server-side.
 */
public final class OpenSslServerSessionContext extends OpenSslSessionContext {
    OpenSslServerSessionContext(ReferenceCountedOpenSslContext context, OpenSslKeyMaterialProvider provider) {
        super(context, provider, SSL.SSL_SESS_CACHE_SERVER, new OpenSslSessionCache(context.engineMap));
    }

    /**
     * Set the context within which session be reused (server side only)
     * See <a href="http://www.openssl.org/docs/ssl/SSL_CTX_set_session_id_context.html">
     * man SSL_CTX_set_session_id_context</a>
     *
     * @param sidCtx can be any kind of binary data, it is therefore possible to use e.g. the name
     *               of the application and/or the hostname and/or service name
     * @return {@code true} if success, {@code false} otherwise.
     */
    public boolean setSessionIdContext(byte[] sidCtx) {
        Lock writerLock = context.ctxLock.writeLock();
        writerLock.lock();
        try {
            return SSLContext.setSessionIdContext(context.ctx, sidCtx);
        } finally {
            writerLock.unlock();
        }
    }
}
