package io.netty.channel.unix;

import io.netty.util.internal.ObjectUtil;

import java.io.File;
import java.net.SocketAddress;

/**
 * A address for a
 * <a href="http://en.wikipedia.org/wiki/Unix_domain_socket">Unix Domain Socket</a>.
 */
public final class DomainSocketAddress extends SocketAddress {
    private static final long serialVersionUID = -6934618000832236893L;
    private final String socketPath;

    public DomainSocketAddress(String socketPath) {
        this.socketPath = ObjectUtil.checkNotNull(socketPath, "socketPath");
    }

    public DomainSocketAddress(File file) {
        this(file.getPath());
    }

    /**
     * The path to the domain socket.
     */
    public String path() {
        return socketPath;
    }

    @Override
    public String toString() {
        return path();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainSocketAddress)) {
            return false;
        }

        return ((DomainSocketAddress) o).socketPath.equals(socketPath);
    }

    @Override
    public int hashCode() {
        return socketPath.hashCode();
    }
}
