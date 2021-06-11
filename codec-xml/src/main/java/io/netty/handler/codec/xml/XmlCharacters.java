package io.netty.handler.codec.xml;

/**
 * XML characters, e.g. &lt;test&gt;characters&lt;/test&gt;, but not CDATA.
 */
public class XmlCharacters extends XmlContent {

    public XmlCharacters(String data) {
        super(data);
    }

}
