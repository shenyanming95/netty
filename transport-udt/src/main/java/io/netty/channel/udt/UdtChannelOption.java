package io.netty.channel.udt;

import com.barchart.udt.OptionUDT;
import io.netty.channel.ChannelOption;

/**
 * Options for the UDT transport
 *
 * @deprecated The UDT transport is no longer maintained and will be removed.
 */
@Deprecated
public final class UdtChannelOption<T> extends ChannelOption<T> {

    /**
     * See {@link OptionUDT#Protocol_Receive_Buffer_Size}.
     */
    public static final ChannelOption<Integer> PROTOCOL_RECEIVE_BUFFER_SIZE = valueOf(UdtChannelOption.class, "PROTOCOL_RECEIVE_BUFFER_SIZE");

    /**
     * See {@link OptionUDT#Protocol_Send_Buffer_Size}.
     */
    public static final ChannelOption<Integer> PROTOCOL_SEND_BUFFER_SIZE = valueOf(UdtChannelOption.class, "PROTOCOL_SEND_BUFFER_SIZE");

    /**
     * See {@link OptionUDT#System_Receive_Buffer_Size}.
     */
    public static final ChannelOption<Integer> SYSTEM_RECEIVE_BUFFER_SIZE = valueOf(UdtChannelOption.class, "SYSTEM_RECEIVE_BUFFER_SIZE");

    /**
     * See {@link OptionUDT#System_Send_Buffer_Size}.
     */
    public static final ChannelOption<Integer> SYSTEM_SEND_BUFFER_SIZE = valueOf(UdtChannelOption.class, "SYSTEM_SEND_BUFFER_SIZE");

    @SuppressWarnings({"unused", "deprecation"})
    private UdtChannelOption() {
        super(null);
    }
}
