package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.v4.Socks4ClientDecoder.State;
import io.netty.util.NetUtil;

import java.util.List;

/**
 * Decodes a single {@link Socks4CommandResponse} from the inbound {@link ByteBuf}s.
 * On successful decode, this decoder will forward the received data to the next handler, so that
 * other handler can remove this decoder later.  On failed decode, this decoder will discard the
 * received data, so that other handler closes the connection later.
 */
public class Socks4ClientDecoder extends ReplayingDecoder<State> {

    public Socks4ClientDecoder() {
        super(State.START);
        setSingleDecode(true);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            switch (state()) {
                case START: {
                    final int version = in.readUnsignedByte();
                    if (version != 0) {
                        throw new DecoderException("unsupported reply version: " + version + " (expected: 0)");
                    }

                    final Socks4CommandStatus status = Socks4CommandStatus.valueOf(in.readByte());
                    final int dstPort = in.readUnsignedShort();
                    final String dstAddr = NetUtil.intToIpAddress(in.readInt());

                    out.add(new DefaultSocks4CommandResponse(status, dstAddr, dstPort));
                    checkpoint(State.SUCCESS);
                }
                case SUCCESS: {
                    int readableBytes = actualReadableBytes();
                    if (readableBytes > 0) {
                        out.add(in.readRetainedSlice(readableBytes));
                    }
                    break;
                }
                case FAILURE: {
                    in.skipBytes(actualReadableBytes());
                    break;
                }
            }
        } catch (Exception e) {
            fail(out, e);
        }
    }

    private void fail(List<Object> out, Exception cause) {
        if (!(cause instanceof DecoderException)) {
            cause = new DecoderException(cause);
        }

        Socks4CommandResponse m = new DefaultSocks4CommandResponse(Socks4CommandStatus.REJECTED_OR_FAILED);
        m.setDecoderResult(DecoderResult.failure(cause));
        out.add(m);

        checkpoint(State.FAILURE);
    }

    enum State {
        START, SUCCESS, FAILURE
    }
}
