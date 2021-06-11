
#include "netty_unix_jni.h"
#include "netty_unix_util.h"
#include "netty_unix_buffer.h"

// JNI Registered Methods Begin
static jlong netty_unix_buffer_memoryAddress0(JNIEnv* env, jclass clazz, jobject buffer) {
    return (jlong) (*env)->GetDirectBufferAddress(env, buffer);
}

static jint netty_unix_buffer_addressSize0(JNIEnv* env, jclass clazz) {
   return (jint) sizeof(int*);
}

// JNI Registered Methods End

// JNI Method Registration Table Begin
static const JNINativeMethod statically_referenced_fixed_method_table[] = {
  { "memoryAddress0", "(Ljava/nio/ByteBuffer;)J", (void *) netty_unix_buffer_memoryAddress0 },
  { "addressSize0", "()I", (void *) netty_unix_buffer_addressSize0 }
};
static const jint statically_referenced_fixed_method_table_size = sizeof(statically_referenced_fixed_method_table) / sizeof(statically_referenced_fixed_method_table[0]);
// JNI Method Registration Table End

jint netty_unix_buffer_JNI_OnLoad(JNIEnv* env, const char* packagePrefix) {
    // We must register the statically referenced methods first!
    if (netty_unix_util_register_natives(env,
            packagePrefix,
            "io/netty/channel/unix/Buffer",
            statically_referenced_fixed_method_table,
            statically_referenced_fixed_method_table_size) != 0) {
        return JNI_ERR;
    }

    return NETTY_JNI_VERSION;
}

void netty_unix_buffer_JNI_OnUnLoad(JNIEnv* env) { }
