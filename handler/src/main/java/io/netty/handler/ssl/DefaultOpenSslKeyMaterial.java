package io.netty.handler.ssl;

import io.netty.internal.tcnative.SSL;
import io.netty.util.*;

import java.security.cert.X509Certificate;

final class DefaultOpenSslKeyMaterial extends AbstractReferenceCounted implements OpenSslKeyMaterial {

    private static final ResourceLeakDetector<DefaultOpenSslKeyMaterial> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(DefaultOpenSslKeyMaterial.class);
    private final ResourceLeakTracker<DefaultOpenSslKeyMaterial> leak;
    private final X509Certificate[] x509CertificateChain;
    private long chain;
    private long privateKey;

    DefaultOpenSslKeyMaterial(long chain, long privateKey, X509Certificate[] x509CertificateChain) {
        this.chain = chain;
        this.privateKey = privateKey;
        this.x509CertificateChain = x509CertificateChain;
        leak = leakDetector.track(this);
    }

    @Override
    public X509Certificate[] certificateChain() {
        return x509CertificateChain.clone();
    }

    @Override
    public long certificateChainAddress() {
        if (refCnt() <= 0) {
            throw new IllegalReferenceCountException();
        }
        return chain;
    }

    @Override
    public long privateKeyAddress() {
        if (refCnt() <= 0) {
            throw new IllegalReferenceCountException();
        }
        return privateKey;
    }

    @Override
    protected void deallocate() {
        SSL.freeX509Chain(chain);
        chain = 0;
        SSL.freePrivateKey(privateKey);
        privateKey = 0;
        if (leak != null) {
            boolean closed = leak.close(this);
            assert closed;
        }
    }

    @Override
    public DefaultOpenSslKeyMaterial retain() {
        if (leak != null) {
            leak.record();
        }
        super.retain();
        return this;
    }

    @Override
    public DefaultOpenSslKeyMaterial retain(int increment) {
        if (leak != null) {
            leak.record();
        }
        super.retain(increment);
        return this;
    }

    @Override
    public DefaultOpenSslKeyMaterial touch() {
        if (leak != null) {
            leak.record();
        }
        super.touch();
        return this;
    }

    @Override
    public DefaultOpenSslKeyMaterial touch(Object hint) {
        if (leak != null) {
            leak.record(hint);
        }
        return this;
    }

    @Override
    public boolean release() {
        if (leak != null) {
            leak.record();
        }
        return super.release();
    }

    @Override
    public boolean release(int decrement) {
        if (leak != null) {
            leak.record();
        }
        return super.release(decrement);
    }
}
