package io.netty.handler.codec.xml;

/**
 * XML Content is base class for XML CDATA, Comments, Characters and Space
 */
public abstract class XmlContent {

    private final String data;

    protected XmlContent(String data) {
        this.data = data;
    }

    public String data() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        XmlContent that = (XmlContent) o;

        if (data != null ? !data.equals(that.data) : that.data != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "XmlContent{" + "data='" + data + '\'' + '}';
    }
}
