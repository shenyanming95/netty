package io.netty.handler.codec.xml;

/**
 * XML processing instruction
 */
public class XmlProcessingInstruction {

    private final String data;
    private final String target;

    public XmlProcessingInstruction(String data, String target) {
        this.data = data;
        this.target = target;
    }

    public String data() {
        return data;
    }

    public String target() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        XmlProcessingInstruction that = (XmlProcessingInstruction) o;

        if (data != null ? !data.equals(that.data) : that.data != null) {
            return false;
        }
        if (target != null ? !target.equals(that.target) : that.target != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XmlProcessingInstruction{" + "data='" + data + '\'' + ", target='" + target + '\'' + '}';
    }

}
