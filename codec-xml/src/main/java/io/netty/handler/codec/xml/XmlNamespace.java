package io.netty.handler.codec.xml;

/**
 * XML namespace is part of XML element.
 */
public class XmlNamespace {

    private final String prefix;
    private final String uri;

    public XmlNamespace(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    public String prefix() {
        return prefix;
    }

    public String uri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        XmlNamespace that = (XmlNamespace) o;

        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) {
            return false;
        }
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = prefix != null ? prefix.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XmlNamespace{" + "prefix='" + prefix + '\'' + ", uri='" + uri + '\'' + '}';
    }

}
