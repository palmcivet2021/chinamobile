package com.chinamobile.jniexample;

public class JNITest {
    static {
        System.loadLibrary("DwgOperInterface");
    }

    public native String getDllFunction();

    public static void main(String[] args) {
        new JNITest().getDllFunction();
    }
}
