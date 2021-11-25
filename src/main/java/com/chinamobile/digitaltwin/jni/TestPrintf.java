package com.chinamobile.digitaltwin.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class TestPrintf {

    public interface CLibrary extends Library {
        //DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径
        CLibrary INSTANCE = (CLibrary) Native.load((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);
        // 声明将要调用的DLL中的方法（可以是多个方法）
        void printf(String format, Object... args);
    }

    public static void main(String[] args){
        CLibrary.INSTANCE.printf("Hello,World!JNA\n");;
        for (int i=0; i<args.length; i++) {
            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
        }
    }
}
