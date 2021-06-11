package io.netty.example.udt.echo.rendezvousBytes;

import io.netty.example.udt.echo.rendezvous.Config;
import io.netty.util.internal.SocketUtils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * UDT Byte Stream Peer
 * <p/>
 * Sends one message when a connection is open and echoes back any received data
 * to the server. Simply put, the echo client initiates the ping-pong traffic
 * between the echo client and server by sending the first message to the
 * server.
 * <p/>
 */
public class ByteEchoPeerTwo extends ByteEchoPeerBase {

    public ByteEchoPeerTwo(int messageSize, SocketAddress myAddress, SocketAddress peerAddress) {
        super(messageSize, myAddress, peerAddress);
    }

    public static void main(String[] args) throws Exception {
        final int messageSize = 64 * 1024;
        final InetSocketAddress myAddress = SocketUtils.socketAddress(Config.hostTwo, Config.portTwo);
        final InetSocketAddress peerAddress = SocketUtils.socketAddress(Config.hostOne, Config.portOne);
        new ByteEchoPeerTwo(messageSize, myAddress, peerAddress).run();
    }
}
