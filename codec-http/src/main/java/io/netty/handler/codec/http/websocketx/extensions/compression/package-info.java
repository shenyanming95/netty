/**
 * Encoder, decoder, handshakers to handle most common WebSocket Compression Extensions.
 * <p>
 * This package supports different web socket extensions.
 * The specification currently supported are:
 * <ul>
 * <li><a href="http://tools.ietf.org/html/draft-ietf-hybi-permessage-compression-18">permessage-deflate</a></li>
 * <li><a href="https://tools.ietf.org/id/draft-tyoshino-hybi-websocket-perframe-deflate-06.txt">
 * perframe-deflate and x-webkit-deflate-frame</a></li>
 * </ul>
 * </p>
 * <p>
 * See <tt>io.netty.example.http.websocketx.client.WebSocketClient</tt> and
 * <tt>io.netty.example.http.websocketx.html5.WebSocketServer</tt> for usage.
 * </p>
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;
