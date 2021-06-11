package io.netty.handler.codec.dns;

import io.netty.util.internal.UnstableApi;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * The DNS {@code OpCode} as defined in <a href="https://tools.ietf.org/html/rfc2929">RFC2929</a>.
 */
@UnstableApi
public class DnsOpCode implements Comparable<DnsOpCode> {

    /**
     * The 'Query' DNS OpCode, as defined in <a href="https://tools.ietf.org/html/rfc1035">RFC1035</a>.
     */
    public static final DnsOpCode QUERY = new DnsOpCode(0x00, "QUERY");

    /**
     * The 'IQuery' DNS OpCode, as defined in <a href="https://tools.ietf.org/html/rfc1035">RFC1035</a>.
     */
    public static final DnsOpCode IQUERY = new DnsOpCode(0x01, "IQUERY");

    /**
     * The 'Status' DNS OpCode, as defined in <a href="https://tools.ietf.org/html/rfc1035">RFC1035</a>.
     */
    public static final DnsOpCode STATUS = new DnsOpCode(0x02, "STATUS");

    /**
     * The 'Notify' DNS OpCode, as defined in <a href="https://tools.ietf.org/html/rfc1996">RFC1996</a>.
     */
    public static final DnsOpCode NOTIFY = new DnsOpCode(0x04, "NOTIFY");

    /**
     * The 'Update' DNS OpCode, as defined in <a href="https://tools.ietf.org/html/rfc2136">RFC2136</a>.
     */
    public static final DnsOpCode UPDATE = new DnsOpCode(0x05, "UPDATE");
    private final byte byteValue;
    private final String name;
    private String text;

    private DnsOpCode(int byteValue) {
        this(byteValue, "UNKNOWN");
    }

    public DnsOpCode(int byteValue, String name) {
        this.byteValue = (byte) byteValue;
        this.name = checkNotNull(name, "name");
    }

    /**
     * Returns the {@link DnsOpCode} instance of the specified byte value.
     */
    public static DnsOpCode valueOf(int b) {
        switch (b) {
            case 0x00:
                return QUERY;
            case 0x01:
                return IQUERY;
            case 0x02:
                return STATUS;
            case 0x04:
                return NOTIFY;
            case 0x05:
                return UPDATE;
        }

        return new DnsOpCode(b);
    }

    public byte byteValue() {
        return byteValue;
    }

    @Override
    public int hashCode() {
        return byteValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DnsOpCode)) {
            return false;
        }

        return byteValue == ((DnsOpCode) obj).byteValue;
    }

    @Override
    public int compareTo(DnsOpCode o) {
        return byteValue - o.byteValue;
    }

    @Override
    public String toString() {
        String text = this.text;
        if (text == null) {
            this.text = text = name + '(' + (byteValue & 0xFF) + ')';
        }
        return text;
    }
}
