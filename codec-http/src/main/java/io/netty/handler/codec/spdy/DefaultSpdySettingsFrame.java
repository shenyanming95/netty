package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The default {@link SpdySettingsFrame} implementation.
 */
public class DefaultSpdySettingsFrame implements SpdySettingsFrame {

    private final Map<Integer, Setting> settingsMap = new TreeMap<Integer, Setting>();
    private boolean clear;

    @Override
    public Set<Integer> ids() {
        return settingsMap.keySet();
    }

    @Override
    public boolean isSet(int id) {
        return settingsMap.containsKey(id);
    }

    @Override
    public int getValue(int id) {
        final Setting setting = settingsMap.get(id);
        return setting != null ? setting.getValue() : -1;
    }

    @Override
    public SpdySettingsFrame setValue(int id, int value) {
        return setValue(id, value, false, false);
    }

    @Override
    public SpdySettingsFrame setValue(int id, int value, boolean persistValue, boolean persisted) {
        if (id < 0 || id > SpdyCodecUtil.SPDY_SETTINGS_MAX_ID) {
            throw new IllegalArgumentException("Setting ID is not valid: " + id);
        }
        final Integer key = Integer.valueOf(id);
        final Setting setting = settingsMap.get(key);
        if (setting != null) {
            setting.setValue(value);
            setting.setPersist(persistValue);
            setting.setPersisted(persisted);
        } else {
            settingsMap.put(key, new Setting(value, persistValue, persisted));
        }
        return this;
    }

    @Override
    public SpdySettingsFrame removeValue(int id) {
        settingsMap.remove(id);
        return this;
    }

    @Override
    public boolean isPersistValue(int id) {
        final Setting setting = settingsMap.get(id);
        return setting != null && setting.isPersist();
    }

    @Override
    public SpdySettingsFrame setPersistValue(int id, boolean persistValue) {
        final Setting setting = settingsMap.get(id);
        if (setting != null) {
            setting.setPersist(persistValue);
        }
        return this;
    }

    @Override
    public boolean isPersisted(int id) {
        final Setting setting = settingsMap.get(id);
        return setting != null && setting.isPersisted();
    }

    @Override
    public SpdySettingsFrame setPersisted(int id, boolean persisted) {
        final Setting setting = settingsMap.get(id);
        if (setting != null) {
            setting.setPersisted(persisted);
        }
        return this;
    }

    @Override
    public boolean clearPreviouslyPersistedSettings() {
        return clear;
    }

    @Override
    public SpdySettingsFrame setClearPreviouslyPersistedSettings(boolean clear) {
        this.clear = clear;
        return this;
    }

    private Set<Map.Entry<Integer, Setting>> getSettings() {
        return settingsMap.entrySet();
    }

    private void appendSettings(StringBuilder buf) {
        for (Map.Entry<Integer, Setting> e : getSettings()) {
            Setting setting = e.getValue();
            buf.append("--> ");
            buf.append(e.getKey());
            buf.append(':');
            buf.append(setting.getValue());
            buf.append(" (persist value: ");
            buf.append(setting.isPersist());
            buf.append("; persisted: ");
            buf.append(setting.isPersisted());
            buf.append(')');
            buf.append(StringUtil.NEWLINE);
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder().append(StringUtil.simpleClassName(this)).append(StringUtil.NEWLINE);
        appendSettings(buf);

        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }

    private static final class Setting {

        private int value;
        private boolean persist;
        private boolean persisted;

        Setting(int value, boolean persist, boolean persisted) {
            this.value = value;
            this.persist = persist;
            this.persisted = persisted;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }

        boolean isPersist() {
            return persist;
        }

        void setPersist(boolean persist) {
            this.persist = persist;
        }

        boolean isPersisted() {
            return persisted;
        }

        void setPersisted(boolean persisted) {
            this.persisted = persisted;
        }
    }
}
