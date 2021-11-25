package com.chinamobile.digitaltwin.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class TestDll {

    public interface TestDllLib extends Library{
        // DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径
        TestDllLib INSTANCE = (TestDllLib) Native.load("NeuronDataReader", TestDllLib.class);

        // 声明将要调用的DLL中的方法（可以是多个方法）
        Pointer BRConnectTo(String server, int port);

    }


    public static void main(String[] args){
        String server = "127.0.0.1";
        int port = 9687;

        Pointer socket = TestDll.TestDllLib.INSTANCE.BRConnectTo(server, port);
        System.out.println(socket);
    }

}
