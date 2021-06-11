package io.netty.handler.codec.dns;

import io.netty.util.internal.StringUtil;
import io.netty.util.internal.UnstableApi;

/**
 * The default {@link DnsQuestion} implementation.
 */
@UnstableApi
public class DefaultDnsQuestion extends AbstractDnsRecord implements DnsQuestion {

    /**
     * Creates a new {@link #CLASS_IN IN-class} question.
     *
     * @param name the domain name of the DNS question
     * @param type the type of the DNS question
     */
    public DefaultDnsQuestion(String name, DnsRecordType type) {
        super(name, type, 0);
    }

    /**
     * Creates a new question.
     *
     * @param name     the domain name of the DNS question
     * @param type     the type of the DNS question
     * @param dnsClass the class of the record, usually one of the following:
     *                 <ul>
     *                     <li>{@link #CLASS_IN}</li>
     *                     <li>{@link #CLASS_CSNET}</li>
     *                     <li>{@link #CLASS_CHAOS}</li>
     *                     <li>{@link #CLASS_HESIOD}</li>
     *                     <li>{@link #CLASS_NONE}</li>
     *                     <li>{@link #CLASS_ANY}</li>
     *                 </ul>
     */
    public DefaultDnsQuestion(String name, DnsRecordType type, int dnsClass) {
        super(name, type, dnsClass, 0);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(64);

        buf.append(StringUtil.simpleClassName(this)).append('(').append(name()).append(' ');

        DnsMessageUtil.appendRecordClass(buf, dnsClass()).append(' ').append(type().name()).append(')');

        return buf.toString();
    }
}
