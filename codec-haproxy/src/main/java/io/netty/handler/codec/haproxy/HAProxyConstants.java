package io.netty.handler.codec.haproxy;

final class HAProxyConstants {

    /**
     * Command byte constants
     */
    static final byte COMMAND_LOCAL_BYTE = 0x00;
    static final byte COMMAND_PROXY_BYTE = 0x01;

    /**
     * Version byte constants
     */
    static final byte VERSION_ONE_BYTE = 0x10;
    static final byte VERSION_TWO_BYTE = 0x20;

    /**
     * Transport protocol byte constants
     */
    static final byte TRANSPORT_UNSPEC_BYTE = 0x00;
    static final byte TRANSPORT_STREAM_BYTE = 0x01;
    static final byte TRANSPORT_DGRAM_BYTE = 0x02;

    /**
     * Address family byte constants
     */
    static final byte AF_UNSPEC_BYTE = 0x00;
    static final byte AF_IPV4_BYTE = 0x10;
    static final byte AF_IPV6_BYTE = 0x20;
    static final byte AF_UNIX_BYTE = 0x30;

    /**
     * Transport protocol and address family byte constants
     */
    static final byte TPAF_UNKNOWN_BYTE = 0x00;
    static final byte TPAF_TCP4_BYTE = 0x11;
    static final byte TPAF_TCP6_BYTE = 0x21;
    static final byte TPAF_UDP4_BYTE = 0x12;
    static final byte TPAF_UDP6_BYTE = 0x22;
    static final byte TPAF_UNIX_STREAM_BYTE = 0x31;
    static final byte TPAF_UNIX_DGRAM_BYTE = 0x32;

    /**
     * V2 protocol binary header prefix
     */
    static final byte[] BINARY_PREFIX = {(byte) 0x0D, (byte) 0x0A, (byte) 0x0D, (byte) 0x0A, (byte) 0x00, (byte) 0x0D, (byte) 0x0A, (byte) 0x51, (byte) 0x55, (byte) 0x49, (byte) 0x54, (byte) 0x0A};

    static final byte[] TEXT_PREFIX = {(byte) 'P', (byte) 'R', (byte) 'O', (byte) 'X', (byte) 'Y',};

    private HAProxyConstants() {
    }
}
