#ifndef NETTY_UNIX_SOCKET_H_
#define NETTY_UNIX_SOCKET_H_

#include <sys/socket.h>
#include <jni.h>

// External C methods
int netty_unix_socket_initSockaddr(JNIEnv* env, jboolean ipv6, jbyteArray address, jint scopeId, jint jport, const struct sockaddr_storage* addr, socklen_t* addrSize);
jbyteArray netty_unix_socket_createInetSocketAddressArray(JNIEnv* env, const struct sockaddr_storage* addr);

int netty_unix_socket_getOption(JNIEnv* env, jint fd, int level, int optname, void* optval, socklen_t optlen);
int netty_unix_socket_setOption(JNIEnv* env, jint fd, int level, int optname, const void* optval, socklen_t len);
int netty_unix_socket_ipAddressLength(const struct sockaddr_storage* addr);

// These method is sometimes needed if you want to special handle some errno value before throwing an exception.
int netty_unix_socket_getOption0(jint fd, int level, int optname, void* optval, socklen_t optlen);
void netty_unix_socket_getOptionHandleError(JNIEnv* env, int err);


// JNI initialization hooks. Users of this file are responsible for calling these in the JNI_OnLoad and JNI_OnUnload methods.
jint netty_unix_socket_JNI_OnLoad(JNIEnv* env, const char* packagePrefix);
void netty_unix_socket_JNI_OnUnLoad(JNIEnv* env);

#endif /* NETTY_UNIX_SOCKET_H_ */
