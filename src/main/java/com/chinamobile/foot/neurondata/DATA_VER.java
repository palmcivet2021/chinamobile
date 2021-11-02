package com.chinamobile.foot.neurondata;

import com.sun.jna.Structure;
import com.sun.jna.Union;

import java.util.Arrays;
import java.util.List;

/**
 * 数据流的版本
 */
@Union.FieldOrder({"_VersionMask", "versionStruct"})
public class DATA_VER extends Union {
    public int _VersionMask;
    public VersionStruct versionStruct;

    public static class ByReference extends DATA_VER implements Union.ByReference {}
    public static class ByValue extends DATA_VER implements Union.ByValue {}


    @Structure.FieldOrder({"BuildNumb", "Revision", "Minor", "Major"})
    public static class VersionStruct extends Structure{
        public byte BuildNumb;//Build number
        public byte Revision; //Revision number
        public byte Minor;    //Subversion number
        public byte Major;    //Major version number
    }


}
