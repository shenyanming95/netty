package io.netty.handler.codec.serialization;

/**
 * please use {@link ClassResolvers} as instance factory
 */
public interface ClassResolver {

    Class<?> resolve(String className) throws ClassNotFoundException;

}
