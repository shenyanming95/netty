package io.netty.handler.ipfilter;

import io.netty.channel.*;
import io.netty.util.internal.ConcurrentSet;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

/**
 * This class allows one to ensure that at all times for every IP address there is at most one
 * {@link Channel} connected to the server.
 */
@ChannelHandler.Sharable
public class UniqueIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {

    private final Set<InetAddress> connected = new ConcurrentSet<InetAddress>();

    @Override
    protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
        final InetAddress remoteIp = remoteAddress.getAddress();
        if (!connected.add(remoteIp)) {
            return false;
        } else {
            ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    connected.remove(remoteIp);
                }
            });
            return true;
        }
    }
}
