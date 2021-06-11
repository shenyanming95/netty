package io.netty.handler.codec.mqtt;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Payload of the {@link MqttSubAckMessage}
 */
public class MqttSubAckPayload {

    private final List<Integer> grantedQoSLevels;

    public MqttSubAckPayload(int... grantedQoSLevels) {
        ObjectUtil.checkNotNull(grantedQoSLevels, "grantedQoSLevels");

        List<Integer> list = new ArrayList<Integer>(grantedQoSLevels.length);
        for (int v : grantedQoSLevels) {
            list.add(v);
        }
        this.grantedQoSLevels = Collections.unmodifiableList(list);
    }

    public MqttSubAckPayload(Iterable<Integer> grantedQoSLevels) {
        ObjectUtil.checkNotNull(grantedQoSLevels, "grantedQoSLevels");
        List<Integer> list = new ArrayList<Integer>();
        for (Integer v : grantedQoSLevels) {
            if (v == null) {
                break;
            }
            list.add(v);
        }
        this.grantedQoSLevels = Collections.unmodifiableList(list);
    }

    public List<Integer> grantedQoSLevels() {
        return grantedQoSLevels;
    }

    @Override
    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName(this)).append('[').append("grantedQoSLevels=").append(grantedQoSLevels).append(']').toString();
    }
}
