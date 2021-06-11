package io.netty.handler.ipfilter;

import java.net.InetSocketAddress;

/**
 * Implement this interface to create new rules.
 */
public interface IpFilterRule {
    /**
     * @return This method should return true if remoteAddress is valid according to your criteria. False otherwise.
     */
    boolean matches(InetSocketAddress remoteAddress);

    /**
     * @return This method should return {@link IpFilterRuleType#ACCEPT} if all
     * {@link IpFilterRule#matches(InetSocketAddress)} for which {@link #matches(InetSocketAddress)}
     * returns true should the accepted. If you want to exclude all of those IP addresses then
     * {@link IpFilterRuleType#REJECT} should be returned.
     */
    IpFilterRuleType ruleType();
}
