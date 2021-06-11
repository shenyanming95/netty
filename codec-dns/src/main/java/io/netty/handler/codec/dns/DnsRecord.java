package io.netty.handler.codec.dns;

import io.netty.util.internal.UnstableApi;

/**
 * A DNS resource record.
 */
@UnstableApi
public interface DnsRecord {

    /**
     * DNS resource record class: {@code IN}
     */
    int CLASS_IN = 0x0001;

    /**
     * DNS resource record class: {@code CSNET}
     */
    int CLASS_CSNET = 0x0002;

    /**
     * DNS resource record class: {@code CHAOS}
     */
    int CLASS_CHAOS = 0x0003;

    /**
     * DNS resource record class: {@code HESIOD}
     */
    int CLASS_HESIOD = 0x0004;

    /**
     * DNS resource record class: {@code NONE}
     */
    int CLASS_NONE = 0x00fe;

    /**
     * DNS resource record class: {@code ANY}
     */
    int CLASS_ANY = 0x00ff;

    /**
     * Returns the name of this resource record.
     */
    String name();

    /**
     * Returns the type of this resource record.
     */
    DnsRecordType type();

    /**
     * Returns the class of this resource record.
     *
     * @return the class value, usually one of the following:
     * <ul>
     *     <li>{@link #CLASS_IN}</li>
     *     <li>{@link #CLASS_CSNET}</li>
     *     <li>{@link #CLASS_CHAOS}</li>
     *     <li>{@link #CLASS_HESIOD}</li>
     *     <li>{@link #CLASS_NONE}</li>
     *     <li>{@link #CLASS_ANY}</li>
     * </ul>
     */
    int dnsClass();

    /**
     * Returns the time to live after reading for this resource record.
     */
    long timeToLive();
}
