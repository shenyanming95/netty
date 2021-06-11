package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.util.internal.ObjectUtil;

import java.util.Collections;
import java.util.Map;

/**
 * A WebSocket Extension data from the <tt>Sec-WebSocket-Extensions</tt> header.
 * <p>
 * See <tt>io.netty.handler.codec.http.HttpHeaders.Names.SEC_WEBSOCKET_EXTENSIONS</tt>.
 */
public final class WebSocketExtensionData {

    private final String name;
    private final Map<String, String> parameters;

    public WebSocketExtensionData(String name, Map<String, String> parameters) {
        this.name = ObjectUtil.checkNotNull(name, "name");
        this.parameters = Collections.unmodifiableMap(ObjectUtil.checkNotNull(parameters, "parameters"));
    }

    /**
     * @return the extension name.
     */
    public String name() {
        return name;
    }

    /**
     * @return the extension optional parameters.
     */
    public Map<String, String> parameters() {
        return parameters;
    }
}
