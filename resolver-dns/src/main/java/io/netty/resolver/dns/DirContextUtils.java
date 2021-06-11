package io.netty.resolver.dns;

import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.List;

final class DirContextUtils {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DirContextUtils.class);

    private DirContextUtils() {
    }

    static void addNameServers(List<InetSocketAddress> defaultNameServers, int defaultPort) {
        // Using jndi-dns to obtain the default name servers.
        //
        // See:
        // - http://docs.oracle.com/javase/8/docs/technotes/guides/jndi/jndi-dns.html
        // - http://mail.openjdk.java.net/pipermail/net-dev/2017-March/010695.html
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        env.put("java.naming.provider.url", "dns://");

        try {
            DirContext ctx = new InitialDirContext(env);
            String dnsUrls = (String) ctx.getEnvironment().get("java.naming.provider.url");
            // Only try if not empty as otherwise we will produce an exception
            if (dnsUrls != null && !dnsUrls.isEmpty()) {
                String[] servers = dnsUrls.split(" ");
                for (String server : servers) {
                    try {
                        URI uri = new URI(server);
                        String host = new URI(server).getHost();

                        if (host == null || host.isEmpty()) {
                            logger.debug("Skipping a nameserver URI as host portion could not be extracted: {}", server);
                            // If the host portion can not be parsed we should just skip this entry.
                            continue;
                        }
                        int port = uri.getPort();
                        defaultNameServers.add(SocketUtils.socketAddress(uri.getHost(), port == -1 ? defaultPort : port));
                    } catch (URISyntaxException e) {
                        logger.debug("Skipping a malformed nameserver URI: {}", server, e);
                    }
                }
            }
        } catch (NamingException ignore) {
            // Will try reflection if this fails.
        }
    }
}
