package io.netty.handler.codec.xml;

/**
 * Specific {@link XmlElement} representing end of element.
 */
public class XmlElementEnd extends XmlElement {

    public XmlElementEnd(String name, String namespace, String prefix) {
        super(name, namespace, prefix);
    }

    @Override
    public String toString() {
        return "XmlElementStart{" + super.toString() + "} ";
    }

}
