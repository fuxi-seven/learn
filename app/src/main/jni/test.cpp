//
// Created by longyun on 20-8-24.
//
#include<jni.h>
//导入我们创建的头文件
#include "com_hly_learn_fragments_JniFragment.h"
JNIEXPORT jstring JNICALL Java_com_hly_learn_fragments_JniFragment_getFromJNI
        (JNIEnv *env, jobject) {
    //返回一个字符串
    return env->NewStringUTF("This is my first NDK Application,my name is seven");
}

JNIEXPORT jstring JNICALL Java_com_hly_learn_fragments_JniFragment_setFromJava
        (JNIEnv * env, jobject ) {
    //通过反射获取上层的java类
    jclass clazz = env->FindClass("com/hly/learn/fragments/JniFragment");
    //实例化该类
    jobject job = env->AllocObject(clazz);
    //得到要调用类的方法
    //参数列表：反射类，方法名称，（参数类型）返回类型
    jmethodID  jmeId = env->GetMethodID(clazz, "getFromJava", "()Ljava/lang/String;");
    //调用方法
    jstring result = (jstring)env->CallObjectMethod(job, jmeId);
    return result;
}