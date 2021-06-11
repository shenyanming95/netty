package io.netty.handler.codec.http.multipart;

import io.netty.util.ReferenceCounted;

/**
 * Interface for all Objects that could be encoded/decoded using HttpPostRequestEncoder/Decoder
 */
public interface InterfaceHttpData extends Comparable<InterfaceHttpData>, ReferenceCounted {
    /**
     * Returns the name of this InterfaceHttpData.
     */
    String getName();

    /**
     * @return The HttpDataType
     */
    HttpDataType getHttpDataType();

    @Override
    InterfaceHttpData retain();

    @Override
    InterfaceHttpData retain(int increment);

    @Override
    InterfaceHttpData touch();

    @Override
    InterfaceHttpData touch(Object hint);

    enum HttpDataType {
        Attribute, FileUpload, InternalAttribute
    }
}
