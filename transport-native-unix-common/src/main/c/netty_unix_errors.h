#ifndef NETTY_UNIX_ERRORS_H_
#define NETTY_UNIX_ERRORS_H_

#include <jni.h>

void netty_unix_errors_throwRuntimeException(JNIEnv* env, char* message);
void netty_unix_errors_throwRuntimeExceptionErrorNo(JNIEnv* env, char* message, int errorNumber);
void netty_unix_errors_throwChannelExceptionErrorNo(JNIEnv* env, char* message, int errorNumber);
void netty_unix_errors_throwIOException(JNIEnv* env, char* message);
void netty_unix_errors_throwIOExceptionErrorNo(JNIEnv* env, char* message, int errorNumber);
void netty_unix_errors_throwPortUnreachableException(JNIEnv* env, char* message);
void netty_unix_errors_throwClosedChannelException(JNIEnv* env);
void netty_unix_errors_throwOutOfMemoryError(JNIEnv* env);

// JNI initialization hooks. Users of this file are responsible for calling these in the JNI_OnLoad and JNI_OnUnload methods.
jint netty_unix_errors_JNI_OnLoad(JNIEnv* env, const char* packagePrefix);
void netty_unix_errors_JNI_OnUnLoad(JNIEnv* env);

#endif /* NETTY_UNIX_ERRORS_H_ */
