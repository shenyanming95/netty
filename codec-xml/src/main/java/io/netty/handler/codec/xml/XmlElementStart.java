package io.netty.handler.codec.xml;

import java.util.LinkedList;
import java.util.List;

/**
 * Specific {@link XmlElement} representing beginning  of element.
 */
public class XmlElementStart extends XmlElement {

    private final List<XmlAttribute> attributes = new LinkedList<XmlAttribute>();

    public XmlElementStart(String name, String namespace, String prefix) {
        super(name, namespace, prefix);
    }

    public List<XmlAttribute> attributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        XmlElementStart that = (XmlElementStart) o;

        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XmlElementStart{" + "attributes=" + attributes + super.toString() + "} ";
    }

}
