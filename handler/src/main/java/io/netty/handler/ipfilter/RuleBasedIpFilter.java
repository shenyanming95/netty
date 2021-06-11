package io.netty.handler.ipfilter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ObjectUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * This class allows one to filter new {@link Channel}s based on the
 * {@link IpFilterRule}s passed to its constructor. If no rules are provided, all connections
 * will be accepted.
 * <p>
 * If you would like to explicitly take action on rejected {@link Channel}s, you should override
 * {@link #channelRejected(ChannelHandlerContext, SocketAddress)}.
 */
@Sharable
public class RuleBasedIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {

    private final IpFilterRule[] rules;

    public RuleBasedIpFilter(IpFilterRule... rules) {
        this.rules = ObjectUtil.checkNotNull(rules, "rules");
    }

    @Override
    protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
        for (IpFilterRule rule : rules) {
            if (rule == null) {
                break;
            }

            if (rule.matches(remoteAddress)) {
                return rule.ruleType() == IpFilterRuleType.ACCEPT;
            }
        }

        return true;
    }
}
