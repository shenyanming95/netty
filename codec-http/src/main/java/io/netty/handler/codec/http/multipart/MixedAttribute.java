package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Mixed implementation using both in Memory and in File with a limit of size
 */
public class MixedAttribute implements Attribute {
    private final long limitSize;
    private String baseDir;
    private boolean deleteOnExit;
    private Attribute attribute;
    private long maxSize = DefaultHttpDataFactory.MAXSIZE;

    public MixedAttribute(String name, long limitSize) {
        this(name, limitSize, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String name, long definedSize, long limitSize) {
        this(name, definedSize, limitSize, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String name, long limitSize, Charset charset) {
        this(name, limitSize, charset, DiskAttribute.baseDirectory, DiskAttribute.deleteOnExitTemporaryFile);
    }

    public MixedAttribute(String name, long limitSize, Charset charset, String baseDir, boolean deleteOnExit) {
        this.limitSize = limitSize;
        attribute = new MemoryAttribute(name, charset);
        this.baseDir = baseDir;
        this.deleteOnExit = deleteOnExit;
    }

    public MixedAttribute(String name, long definedSize, long limitSize, Charset charset) {
        this(name, definedSize, limitSize, charset, DiskAttribute.baseDirectory, DiskAttribute.deleteOnExitTemporaryFile);
    }

    public MixedAttribute(String name, long definedSize, long limitSize, Charset charset, String baseDir, boolean deleteOnExit) {
        this.limitSize = limitSize;
        attribute = new MemoryAttribute(name, definedSize, charset);
        this.baseDir = baseDir;
        this.deleteOnExit = deleteOnExit;
    }

    public MixedAttribute(String name, String value, long limitSize) {
        this(name, value, limitSize, HttpConstants.DEFAULT_CHARSET, DiskAttribute.baseDirectory, DiskFileUpload.deleteOnExitTemporaryFile);
    }

    public MixedAttribute(String name, String value, long limitSize, Charset charset) {
        this(name, value, limitSize, charset, DiskAttribute.baseDirectory, DiskFileUpload.deleteOnExitTemporaryFile);
    }

    public MixedAttribute(String name, String value, long limitSize, Charset charset, String baseDir, boolean deleteOnExit) {
        this.limitSize = limitSize;
        if (value.length() > this.limitSize) {
            try {
                attribute = new DiskAttribute(name, value, charset, baseDir, deleteOnExit);
            } catch (IOException e) {
                // revert to Memory mode
                try {
                    attribute = new MemoryAttribute(name, value, charset);
                } catch (IOException ignore) {
                    throw new IllegalArgumentException(e);
                }
            }
        } else {
            try {
                attribute = new MemoryAttribute(name, value, charset);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        this.baseDir = baseDir;
        this.deleteOnExit = deleteOnExit;
    }

    @Override
    public long getMaxSize() {
        return maxSize;
    }

    @Override
    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
        attribute.setMaxSize(maxSize);
    }

    @Override
    public void checkSize(long newSize) throws IOException {
        if (maxSize >= 0 && newSize > maxSize) {
            throw new IOException("Size exceed allowed maximum capacity");
        }
    }

    @Override
    public void addContent(ByteBuf buffer, boolean last) throws IOException {
        if (attribute instanceof MemoryAttribute) {
            checkSize(attribute.length() + buffer.readableBytes());
            if (attribute.length() + buffer.readableBytes() > limitSize) {
                DiskAttribute diskAttribute = new DiskAttribute(attribute.getName(), attribute.definedLength(), baseDir, deleteOnExit);
                diskAttribute.setMaxSize(maxSize);
                if (((MemoryAttribute) attribute).getByteBuf() != null) {
                    diskAttribute.addContent(((MemoryAttribute) attribute).getByteBuf(), false);
                }
                attribute = diskAttribute;
            }
        }
        attribute.addContent(buffer, last);
    }

    @Override
    public void delete() {
        attribute.delete();
    }

    @Override
    public byte[] get() throws IOException {
        return attribute.get();
    }

    @Override
    public ByteBuf getByteBuf() throws IOException {
        return attribute.getByteBuf();
    }

    @Override
    public Charset getCharset() {
        return attribute.getCharset();
    }

    @Override
    public void setCharset(Charset charset) {
        attribute.setCharset(charset);
    }

    @Override
    public String getString() throws IOException {
        return attribute.getString();
    }

    @Override
    public String getString(Charset encoding) throws IOException {
        return attribute.getString(encoding);
    }

    @Override
    public boolean isCompleted() {
        return attribute.isCompleted();
    }

    @Override
    public boolean isInMemory() {
        return attribute.isInMemory();
    }

    @Override
    public long length() {
        return attribute.length();
    }

    @Override
    public long definedLength() {
        return attribute.definedLength();
    }

    @Override
    public boolean renameTo(File dest) throws IOException {
        return attribute.renameTo(dest);
    }

    @Override
    public void setContent(ByteBuf buffer) throws IOException {
        checkSize(buffer.readableBytes());
        if (buffer.readableBytes() > limitSize) {
            if (attribute instanceof MemoryAttribute) {
                // change to Disk
                attribute = new DiskAttribute(attribute.getName(), attribute.definedLength(), baseDir, deleteOnExit);
                attribute.setMaxSize(maxSize);
            }
        }
        attribute.setContent(buffer);
    }

    @Override
    public void setContent(File file) throws IOException {
        checkSize(file.length());
        if (file.length() > limitSize) {
            if (attribute instanceof MemoryAttribute) {
                // change to Disk
                attribute = new DiskAttribute(attribute.getName(), attribute.definedLength(), baseDir, deleteOnExit);
                attribute.setMaxSize(maxSize);
            }
        }
        attribute.setContent(file);
    }

    @Override
    public void setContent(InputStream inputStream) throws IOException {
        if (attribute instanceof MemoryAttribute) {
            // change to Disk even if we don't know the size
            attribute = new DiskAttribute(attribute.getName(), attribute.definedLength(), baseDir, deleteOnExit);
            attribute.setMaxSize(maxSize);
        }
        attribute.setContent(inputStream);
    }

    @Override
    public HttpDataType getHttpDataType() {
        return attribute.getHttpDataType();
    }

    @Override
    public String getName() {
        return attribute.getName();
    }

    @Override
    public int hashCode() {
        return attribute.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return attribute.equals(obj);
    }

    @Override
    public int compareTo(InterfaceHttpData o) {
        return attribute.compareTo(o);
    }

    @Override
    public String toString() {
        return "Mixed: " + attribute;
    }

    @Override
    public String getValue() throws IOException {
        return attribute.getValue();
    }

    @Override
    public void setValue(String value) throws IOException {
        if (value != null) {
            checkSize(value.getBytes().length);
        }
        attribute.setValue(value);
    }

    @Override
    public ByteBuf getChunk(int length) throws IOException {
        return attribute.getChunk(length);
    }

    @Override
    public File getFile() throws IOException {
        return attribute.getFile();
    }

    @Override
    public Attribute copy() {
        return attribute.copy();
    }

    @Override
    public Attribute duplicate() {
        return attribute.duplicate();
    }

    @Override
    public Attribute retainedDuplicate() {
        return attribute.retainedDuplicate();
    }

    @Override
    public Attribute replace(ByteBuf content) {
        return attribute.replace(content);
    }

    @Override
    public ByteBuf content() {
        return attribute.content();
    }

    @Override
    public int refCnt() {
        return attribute.refCnt();
    }

    @Override
    public Attribute retain() {
        attribute.retain();
        return this;
    }

    @Override
    public Attribute retain(int increment) {
        attribute.retain(increment);
        return this;
    }

    @Override
    public Attribute touch() {
        attribute.touch();
        return this;
    }

    @Override
    public Attribute touch(Object hint) {
        attribute.touch(hint);
        return this;
    }

    @Override
    public boolean release() {
        return attribute.release();
    }

    @Override
    public boolean release(int decrement) {
        return attribute.release(decrement);
    }
}
