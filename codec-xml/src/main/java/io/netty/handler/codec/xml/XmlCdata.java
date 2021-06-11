package io.netty.handler.codec.xml;

/**
 * XML CDATA ... <![CDATA[&lt;sender&gt;John Smith&lt;/sender&gt;]]>
 */
public class XmlCdata extends XmlContent {

    public XmlCdata(String data) {
        super(data);
    }

}
