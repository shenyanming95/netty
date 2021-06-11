package io.netty.handler.codec.socksx;

import io.netty.handler.codec.DecoderResultProvider;

/**
 * An interface that all SOCKS protocol messages implement.
 */
public interface SocksMessage extends DecoderResultProvider {

    /**
     * Returns the protocol version of this message.
     */
    SocksVersion version();
}
