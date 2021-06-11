package io.netty.handler.codec.serialization;

import io.netty.util.internal.PlatformDependent;

import java.lang.ref.Reference;
import java.util.HashMap;

public final class ClassResolvers {

    private ClassResolvers() {
        // Unused
    }

    /**
     * cache disabled
     *
     * @param classLoader - specific classLoader to use, or null if you want to revert to default
     * @return new instance of class resolver
     */
    public static ClassResolver cacheDisabled(ClassLoader classLoader) {
        return new ClassLoaderClassResolver(defaultClassLoader(classLoader));
    }

    /**
     * non-aggressive non-concurrent cache
     * good for non-shared default cache
     *
     * @param classLoader - specific classLoader to use, or null if you want to revert to default
     * @return new instance of class resolver
     */
    public static ClassResolver weakCachingResolver(ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new WeakReferenceMap<String, Class<?>>(new HashMap<String, Reference<Class<?>>>()));
    }

    /**
     * aggressive non-concurrent cache
     * good for non-shared cache, when we're not worried about class unloading
     *
     * @param classLoader - specific classLoader to use, or null if you want to revert to default
     * @return new instance of class resolver
     */
    public static ClassResolver softCachingResolver(ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new SoftReferenceMap<String, Class<?>>(new HashMap<String, Reference<Class<?>>>()));
    }

    /**
     * non-aggressive concurrent cache
     * good for shared cache, when we're worried about class unloading
     *
     * @param classLoader - specific classLoader to use, or null if you want to revert to default
     * @return new instance of class resolver
     */
    public static ClassResolver weakCachingConcurrentResolver(ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new WeakReferenceMap<String, Class<?>>(PlatformDependent.<String, Reference<Class<?>>>newConcurrentHashMap()));
    }

    /**
     * aggressive concurrent cache
     * good for shared cache, when we're not worried about class unloading
     *
     * @param classLoader - specific classLoader to use, or null if you want to revert to default
     * @return new instance of class resolver
     */
    public static ClassResolver softCachingConcurrentResolver(ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new SoftReferenceMap<String, Class<?>>(PlatformDependent.<String, Reference<Class<?>>>newConcurrentHashMap()));
    }

    static ClassLoader defaultClassLoader(ClassLoader classLoader) {
        if (classLoader != null) {
            return classLoader;
        }

        final ClassLoader contextClassLoader = PlatformDependent.getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }

        return PlatformDependent.getClassLoader(ClassResolvers.class);
    }
}
