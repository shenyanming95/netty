package io.netty.handler.codec.http;

import io.netty.util.internal.ObjectUtil;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * The default {@link HttpMessage} implementation.
 */
public abstract class DefaultHttpMessage extends DefaultHttpObject implements HttpMessage {
    private static final int HASH_CODE_PRIME = 31;
    private final HttpHeaders headers;
    private HttpVersion version;

    /**
     * Creates a new instance.
     */
    protected DefaultHttpMessage(final HttpVersion version) {
        this(version, true, false);
    }

    /**
     * Creates a new instance.
     */
    protected DefaultHttpMessage(final HttpVersion version, boolean validateHeaders, boolean singleFieldHeaders) {
        this(version, singleFieldHeaders ? new CombinedHttpHeaders(validateHeaders) : new DefaultHttpHeaders(validateHeaders));
    }

    /**
     * Creates a new instance.
     */
    protected DefaultHttpMessage(final HttpVersion version, HttpHeaders headers) {
        this.version = checkNotNull(version, "version");
        this.headers = checkNotNull(headers, "headers");
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }

    @Override
    @Deprecated
    public HttpVersion getProtocolVersion() {
        return protocolVersion();
    }

    @Override
    public HttpVersion protocolVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = HASH_CODE_PRIME * result + headers.hashCode();
        result = HASH_CODE_PRIME * result + version.hashCode();
        result = HASH_CODE_PRIME * result + super.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttpMessage)) {
            return false;
        }

        DefaultHttpMessage other = (DefaultHttpMessage) o;

        return headers().equals(other.headers()) && protocolVersion().equals(other.protocolVersion()) && super.equals(o);
    }

    @Override
    public HttpMessage setProtocolVersion(HttpVersion version) {
        this.version = ObjectUtil.checkNotNull(version, "version");
        return this;
    }
}
