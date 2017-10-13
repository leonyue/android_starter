//
// Created by dj.yue on 2017/10/13.
//

#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_leonyue_android_1starter_MainActivity_getStringFromJni
        (JNIEnv *env, jclass obj) {
    char *str = "Hello, NDK";
    jstring jstr = (*env)->NewStringUTF(env, str);
    return jstr;
}
