package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;

import javax.net.ssl.X509KeyManager;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link OpenSslKeyMaterialProvider} that will cache the {@link OpenSslKeyMaterial} to reduce the overhead
 * of parsing the chain and the key for generation of the material.
 */
final class OpenSslCachingKeyMaterialProvider extends OpenSslKeyMaterialProvider {

    private final int maxCachedEntries;
    private final ConcurrentMap<String, OpenSslKeyMaterial> cache = new ConcurrentHashMap<String, OpenSslKeyMaterial>();
    private volatile boolean full;

    OpenSslCachingKeyMaterialProvider(X509KeyManager keyManager, String password, int maxCachedEntries) {
        super(keyManager, password);
        this.maxCachedEntries = maxCachedEntries;
    }

    @Override
    OpenSslKeyMaterial chooseKeyMaterial(ByteBufAllocator allocator, String alias) throws Exception {
        OpenSslKeyMaterial material = cache.get(alias);
        if (material == null) {
            material = super.chooseKeyMaterial(allocator, alias);
            if (material == null) {
                // No keymaterial should be used.
                return null;
            }

            if (full) {
                return material;
            }
            if (cache.size() > maxCachedEntries) {
                full = true;
                // Do not cache...
                return material;
            }
            OpenSslKeyMaterial old = cache.putIfAbsent(alias, material);
            if (old != null) {
                material.release();
                material = old;
            }
        }
        // We need to call retain() as we want to always have at least a refCnt() of 1 before destroy() was called.
        return material.retain();
    }

    @Override
    void destroy() {
        // Remove and release all entries.
        do {
            Iterator<OpenSslKeyMaterial> iterator = cache.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().release();
                iterator.remove();
            }
        } while (!cache.isEmpty());
    }
}
