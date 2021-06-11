package io.netty.handler.codec.memcache;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequestDecoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponseEncoder;
import io.netty.util.internal.UnstableApi;

/**
 * A {@link ChannelHandler} that aggregates an {@link MemcacheMessage}
 * and its following {@link MemcacheContent}s into a single {@link MemcacheMessage} with
 * no following {@link MemcacheContent}s.  It is useful when you don't want to take
 * care of memcache messages where the content comes along in chunks. Insert this
 * handler after a AbstractMemcacheObjectDecoder in the {@link ChannelPipeline}.
 * <p/>
 * For example, here for the binary protocol:
 * <p/>
 * <pre>
 * {@link ChannelPipeline} p = ...;
 * ...
 * p.addLast("decoder", new {@link BinaryMemcacheRequestDecoder}());
 * p.addLast("aggregator", <b>new {@link io.netty.handler.codec.memcache.binary.BinaryMemcacheObjectAggregator}(1048576)
 * </b>);
 * ...
 * p.addLast("encoder", new {@link BinaryMemcacheResponseEncoder}());
 * p.addLast("handler", new YourMemcacheRequestHandler());
 * </pre>
 */
@UnstableApi
public abstract class AbstractMemcacheObjectAggregator<H extends MemcacheMessage> extends MessageAggregator<MemcacheObject, H, MemcacheContent, FullMemcacheMessage> {

    protected AbstractMemcacheObjectAggregator(int maxContentLength) {
        super(maxContentLength);
    }

    @Override
    protected boolean isContentMessage(MemcacheObject msg) throws Exception {
        return msg instanceof MemcacheContent;
    }

    @Override
    protected boolean isLastContentMessage(MemcacheContent msg) throws Exception {
        return msg instanceof LastMemcacheContent;
    }

    @Override
    protected boolean isAggregated(MemcacheObject msg) throws Exception {
        return msg instanceof FullMemcacheMessage;
    }

    @Override
    protected boolean isContentLengthInvalid(H start, int maxContentLength) {
        return false;
    }

    @Override
    protected Object newContinueResponse(H start, int maxContentLength, ChannelPipeline pipeline) {
        return null;
    }

    @Override
    protected boolean closeAfterContinueResponse(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean ignoreContentAfterContinueResponse(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }
}
