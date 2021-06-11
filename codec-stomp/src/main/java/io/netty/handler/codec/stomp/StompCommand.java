package io.netty.handler.codec.stomp;

/**
 * STOMP command
 */
public enum StompCommand {
    STOMP, CONNECT, CONNECTED, SEND, SUBSCRIBE, UNSUBSCRIBE, ACK, NACK, BEGIN, DISCONNECT, MESSAGE, RECEIPT, ERROR, UNKNOWN
}
