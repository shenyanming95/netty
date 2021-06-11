
#ifndef NETTY_UNIX_BUFFER_H_
#define NETTY_UNIX_BUFFER_H_

#include <jni.h>

// JNI initialization hooks. Users of this file are responsible for calling these in the JNI_OnLoad and JNI_OnUnload methods.
jint netty_unix_buffer_JNI_OnLoad(JNIEnv* env, const char* packagePrefix);
void netty_unix_buffer_JNI_OnUnLoad(JNIEnv* env);

#endif /* NETTY_UNIX_BUFFER_H_ */
