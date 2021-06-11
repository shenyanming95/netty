package io.netty.example.udt.echo.rendezvous;

import io.netty.util.internal.SocketUtils;

import java.net.InetSocketAddress;

/**
 * UDT Message Flow Peer
 * <p>
 * Sends one message when a connection is open and echoes back any received data
 * to the other peer.
 */
public class MsgEchoPeerOne extends MsgEchoPeerBase {

    public MsgEchoPeerOne(final InetSocketAddress self, final InetSocketAddress peer, final int messageSize) {
        super(self, peer, messageSize);
    }

    public static void main(final String[] args) throws Exception {
        final int messageSize = 64 * 1024;
        final InetSocketAddress self = SocketUtils.socketAddress(Config.hostOne, Config.portOne);
        final InetSocketAddress peer = SocketUtils.socketAddress(Config.hostTwo, Config.portTwo);
        new MsgEchoPeerOne(self, peer, messageSize).run();
    }
}
