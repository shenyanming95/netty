package io.netty.handler.codec.xml;

/**
 * XML entity reference ... {@code &#nnnn;}
 */
public class XmlEntityReference {

    private final String name;
    private final String text;

    public XmlEntityReference(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String name() {
        return name;
    }

    public String text() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        XmlEntityReference that = (XmlEntityReference) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (text != null ? !text.equals(that.text) : that.text != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XmlEntityReference{" + "name='" + name + '\'' + ", text='" + text + '\'' + '}';
    }
}
