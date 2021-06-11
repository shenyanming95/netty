package io.netty.channel;

import io.netty.util.concurrent.PromiseAggregator;
import io.netty.util.concurrent.PromiseCombiner;

/**
 * @deprecated Use {@link PromiseCombiner}
 * <p>
 * Class which is used to consolidate multiple channel futures into one, by
 * listening to the individual futures and producing an aggregated result
 * (success/failure) when all futures have completed.
 */
@Deprecated
public final class ChannelPromiseAggregator extends PromiseAggregator<Void, ChannelFuture> implements ChannelFutureListener {

    public ChannelPromiseAggregator(ChannelPromise aggregatePromise) {
        super(aggregatePromise);
    }

}
