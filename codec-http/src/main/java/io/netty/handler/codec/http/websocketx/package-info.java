/**
 * Encoder, decoder, handshakers and their related message types for
 * <a href="http://en.wikipedia.org/wiki/Web_Sockets">Web Socket</a> data frames.
 * <p>
 * This package supports different web socket specification versions (hence the X suffix).
 * The specification current supported are:
 * <ul>
 * <li><a href="https://netty.io/s/ws-00">draft-ietf-hybi-thewebsocketprotocol-00</a></li>
 * <li><a href="https://netty.io/s/ws-07">draft-ietf-hybi-thewebsocketprotocol-07</a></li>
 * <li><a href="https://netty.io/s/ws-10">draft-ietf-hybi-thewebsocketprotocol-10</a></li>
 * <li><a href="https://netty.io/s/rfc6455">RFC 6455</a>
 *     (originally <a href="https://netty.io/s/ws-17">draft-ietf-hybi-thewebsocketprotocol-17</a>)</li>
 *
 * </ul>
 * </p>
 * <p>
 * For the detailed instruction on adding add Web Socket support to your HTTP
 * server, take a look into the <tt>WebSocketServerX</tt> example located in the
 * {@code io.netty.example.http.websocket} package.
 * </p>
 */
package io.netty.handler.codec.http.websocketx;

