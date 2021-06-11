package io.netty.handler.codec.http.websocketx.extensions;


/**
 * Created once the handshake phase is done.
 */
public interface WebSocketServerExtension extends WebSocketExtension {

    /**
     * Return an extension configuration to submit to the client as an acknowledge.
     *
     * @return the acknowledged extension configuration.
     */
    //TODO: after migrating to JDK 8 rename this to 'newResponseData()' and mark old as deprecated with default method
    WebSocketExtensionData newReponseData();

}
