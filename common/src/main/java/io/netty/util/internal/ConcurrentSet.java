package io.netty.util.internal;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

/**
 * @deprecated For removal in Netty 4.2. Please use {@link ConcurrentHashMap#newKeySet()} instead
 */
@Deprecated
public final class ConcurrentSet<E> extends AbstractSet<E> implements Serializable {

    private static final long serialVersionUID = -6761513279741915432L;

    private final ConcurrentMap<E, Boolean> map;

    /**
     * Creates a new instance which wraps the specified {@code map}.
     */
    public ConcurrentSet() {
        map = PlatformDependent.newConcurrentHashMap();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(E o) {
        return map.putIfAbsent(o, Boolean.TRUE) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }
}
