package com.chinamobile.digitaltwin.jni;

public class HelloJava {
    public void helloWorld(){
        System.out.println("Hello Java not Jni!");
    }

    public static void main(String[] args){
        new HelloJava().helloWorld();
    }
}
