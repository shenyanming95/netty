package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Attribute interface
 */
public interface Attribute extends HttpData {
    /**
     * Returns the value of this HttpData.
     */
    String getValue() throws IOException;

    /**
     * Sets the value of this HttpData.
     */
    void setValue(String value) throws IOException;

    @Override
    Attribute copy();

    @Override
    Attribute duplicate();

    @Override
    Attribute retainedDuplicate();

    @Override
    Attribute replace(ByteBuf content);

    @Override
    Attribute retain();

    @Override
    Attribute retain(int increment);

    @Override
    Attribute touch();

    @Override
    Attribute touch(Object hint);
}
