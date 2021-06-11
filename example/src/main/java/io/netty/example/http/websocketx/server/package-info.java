/**
 * <p>This package contains an example web socket web server.
 * <p>The web server only handles text, ping and closing frames.  For text frames,
 * it echoes the received text in upper case.
 * <p>Once started, you can test the web server against your browser by navigating
 * to http://localhost:8080/
 * <p>You can also test it with a web socket client. Send web socket traffic to
 * ws://localhost:8080/websocket.
 */
package io.netty.example.http.websocketx.server;
