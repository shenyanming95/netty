package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpRequest;

import java.nio.charset.Charset;

/**
 * Interface to enable creation of InterfaceHttpData objects
 */
public interface HttpDataFactory {

    /**
     * To set a max size limitation on fields. Exceeding it will generate an ErrorDataDecoderException.
     * A value of -1 means no limitation (default).
     */
    void setMaxLimit(long max);

    /**
     * @param request associated request
     * @return a new Attribute with no value
     */
    Attribute createAttribute(HttpRequest request, String name);

    /**
     * @param request     associated request
     * @param name        name of the attribute
     * @param definedSize defined size from request for this attribute
     * @return a new Attribute with no value
     */
    Attribute createAttribute(HttpRequest request, String name, long definedSize);

    /**
     * @param request associated request
     * @return a new Attribute
     */
    Attribute createAttribute(HttpRequest request, String name, String value);

    /**
     * @param request associated request
     * @param size    the size of the Uploaded file
     * @return a new FileUpload
     */
    FileUpload createFileUpload(HttpRequest request, String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size);

    /**
     * Remove the given InterfaceHttpData from clean list (will not delete the file, except if the file
     * is still a temporary one as setup at construction)
     *
     * @param request associated request
     */
    void removeHttpDataFromClean(HttpRequest request, InterfaceHttpData data);

    /**
     * Remove all InterfaceHttpData from virtual File storage from clean list for the request
     *
     * @param request associated request
     */
    void cleanRequestHttpData(HttpRequest request);

    /**
     * Remove all InterfaceHttpData from virtual File storage from clean list for all requests
     */
    void cleanAllHttpData();

    /**
     * @deprecated Use {@link #cleanRequestHttpData(HttpRequest)} instead.
     */
    @Deprecated
    void cleanRequestHttpDatas(HttpRequest request);

    /**
     * @deprecated Use {@link #cleanAllHttpData()} instead.
     */
    @Deprecated
    void cleanAllHttpDatas();
}
