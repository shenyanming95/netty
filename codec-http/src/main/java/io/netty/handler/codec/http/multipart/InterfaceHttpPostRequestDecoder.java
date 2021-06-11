package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpContent;

import java.util.List;

/**
 * This decoder will decode Body and can handle POST BODY.
 * <p>
 * You <strong>MUST</strong> call {@link #destroy()} after completion to release all resources.
 */
public interface InterfaceHttpPostRequestDecoder {
    /**
     * True if this request is a Multipart request
     *
     * @return True if this request is a Multipart request
     */
    boolean isMultipart();

    /**
     * Return the threshold in bytes after which read data in the buffer should be discarded.
     */
    int getDiscardThreshold();

    /**
     * Set the amount of bytes after which read bytes in the buffer should be discarded.
     * Setting this lower gives lower memory usage but with the overhead of more memory copies.
     * Use {@code 0} to disable it.
     */
    void setDiscardThreshold(int discardThreshold);

    /**
     * This getMethod returns a List of all HttpDatas from body.<br>
     * <p>
     * If chunked, all chunks must have been offered using offer() getMethod. If
     * not, NotEnoughDataDecoderException will be raised.
     *
     * @return the list of HttpDatas from Body part for POST getMethod
     * @throws HttpPostRequestDecoder.NotEnoughDataDecoderException Need more chunks
     */
    List<InterfaceHttpData> getBodyHttpDatas();

    /**
     * This getMethod returns a List of all HttpDatas with the given name from
     * body.<br>
     * <p>
     * If chunked, all chunks must have been offered using offer() getMethod. If
     * not, NotEnoughDataDecoderException will be raised.
     *
     * @return All Body HttpDatas with the given name (ignore case)
     * @throws HttpPostRequestDecoder.NotEnoughDataDecoderException need more chunks
     */
    List<InterfaceHttpData> getBodyHttpDatas(String name);

    /**
     * This getMethod returns the first InterfaceHttpData with the given name from
     * body.<br>
     * <p>
     * If chunked, all chunks must have been offered using offer() getMethod. If
     * not, NotEnoughDataDecoderException will be raised.
     *
     * @return The first Body InterfaceHttpData with the given name (ignore
     * case)
     * @throws HttpPostRequestDecoder.NotEnoughDataDecoderException need more chunks
     */
    InterfaceHttpData getBodyHttpData(String name);

    /**
     * Initialized the internals from a new chunk
     *
     * @param content the new received chunk
     * @throws HttpPostRequestDecoder.ErrorDataDecoderException if there is a problem with the charset decoding or other
     *                                                          errors
     */
    InterfaceHttpPostRequestDecoder offer(HttpContent content);

    /**
     * True if at current getStatus, there is an available decoded
     * InterfaceHttpData from the Body.
     * <p>
     * This getMethod works for chunked and not chunked request.
     *
     * @return True if at current getStatus, there is a decoded InterfaceHttpData
     * @throws HttpPostRequestDecoder.EndOfDataDecoderException No more data will be available
     */
    boolean hasNext();

    /**
     * Returns the next available InterfaceHttpData or null if, at the time it
     * is called, there is no more available InterfaceHttpData. A subsequent
     * call to offer(httpChunk) could enable more data.
     * <p>
     * Be sure to call {@link InterfaceHttpData#release()} after you are done
     * with processing to make sure to not leak any resources
     *
     * @return the next available InterfaceHttpData or null if none
     * @throws HttpPostRequestDecoder.EndOfDataDecoderException No more data will be available
     */
    InterfaceHttpData next();

    /**
     * Returns the current InterfaceHttpData if currently in decoding status,
     * meaning all data are not yet within, or null if there is no InterfaceHttpData
     * currently in decoding status (either because none yet decoded or none currently partially
     * decoded). Full decoded ones are accessible through hasNext() and next() methods.
     *
     * @return the current InterfaceHttpData if currently in decoding status or null if none.
     */
    InterfaceHttpData currentPartialHttpData();

    /**
     * Destroy the {@link InterfaceHttpPostRequestDecoder} and release all it resources. After this method
     * was called it is not possible to operate on it anymore.
     */
    void destroy();

    /**
     * Clean all HttpDatas (on Disk) for the current request.
     */
    void cleanFiles();

    /**
     * Remove the given FileUpload from the list of FileUploads to clean
     */
    void removeHttpDataFromClean(InterfaceHttpData data);
}
