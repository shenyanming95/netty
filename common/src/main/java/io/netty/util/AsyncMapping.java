package io.netty.util;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

public interface AsyncMapping<IN, OUT> {

    /**
     * Returns the {@link Future} that will provide the result of the mapping. The given {@link Promise} will
     * be fulfilled when the result is available.
     */
    Future<OUT> map(IN input, Promise<OUT> promise);
}
