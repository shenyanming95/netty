#ifndef NETTY_UNIX_FILEDESCRIPTOR_H_
#define NETTY_UNIX_FILEDESCRIPTOR_H_

#include <jni.h>

// JNI initialization hooks. Users of this file are responsible for calling these in the JNI_OnLoad and JNI_OnUnload methods.
jint netty_unix_filedescriptor_JNI_OnLoad(JNIEnv* env, const char* packagePrefix);
void netty_unix_filedescriptor_JNI_OnUnLoad(JNIEnv* env);

#endif /* NETTY_UNIX_FILEDESCRIPTOR_H_ */
