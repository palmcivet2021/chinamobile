package com.chinamobile.foot.jni;

public class HelloJni {
    public native void helloWorld(); // 注意，这个native方法就是调用C语言接口用的
    static{
        System.loadLibrary("hello");  // 这行是调用动态链接库
        //System.load("/root/ctest/libhello.so");
    }
    public static void main(String[] args){
        new HelloJni().helloWorld();
    }
}
