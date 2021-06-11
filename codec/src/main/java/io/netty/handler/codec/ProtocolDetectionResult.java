package io.netty.handler.codec;

import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * Result of detecting a protocol.
 *
 * @param <T> the type of the protocol
 */
public final class ProtocolDetectionResult<T> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final ProtocolDetectionResult NEEDS_MORE_DATA = new ProtocolDetectionResult(ProtocolDetectionState.NEEDS_MORE_DATA, null);
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final ProtocolDetectionResult INVALID = new ProtocolDetectionResult(ProtocolDetectionState.INVALID, null);

    private final ProtocolDetectionState state;
    private final T result;

    private ProtocolDetectionResult(ProtocolDetectionState state, T result) {
        this.state = state;
        this.result = result;
    }

    /**
     * Returns a {@link ProtocolDetectionResult} that signals that more data is needed to detect the protocol.
     */
    @SuppressWarnings("unchecked")
    public static <T> ProtocolDetectionResult<T> needsMoreData() {
        return NEEDS_MORE_DATA;
    }

    /**
     * Returns a {@link ProtocolDetectionResult} that signals the data was invalid for the protocol.
     */
    @SuppressWarnings("unchecked")
    public static <T> ProtocolDetectionResult<T> invalid() {
        return INVALID;
    }

    /**
     * Returns a {@link ProtocolDetectionResult} which holds the detected protocol.
     */
    @SuppressWarnings("unchecked")
    public static <T> ProtocolDetectionResult<T> detected(T protocol) {
        return new ProtocolDetectionResult<T>(ProtocolDetectionState.DETECTED, checkNotNull(protocol, "protocol"));
    }

    /**
     * Return the {@link ProtocolDetectionState}. If the state is {@link ProtocolDetectionState#DETECTED} you
     * can retrieve the protocol via {@link #detectedProtocol()}.
     */
    public ProtocolDetectionState state() {
        return state;
    }

    /**
     * Returns the protocol if {@link #state()} returns {@link ProtocolDetectionState#DETECTED}, otherwise {@code null}.
     */
    public T detectedProtocol() {
        return result;
    }
}
