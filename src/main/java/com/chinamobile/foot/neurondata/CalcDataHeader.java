package com.chinamobile.foot.neurondata;


import com.sun.jna.Structure;

/**
 * CalcData数据流头
 */
@Structure.FieldOrder({"Token1", "DataVersion", "DataCount", "AvatarIndex","AvatarName", "FrameIndex", "Reserved1", "Reserved2", "Reserved3", "Token2"})
public class CalcDataHeader extends Structure {
    public short Token1;           // Package start token: 0xDDFF
    public DATA_VER DataVersion;      // Version of community data format. e.g.: 1.0.0.2
    public short DataCount;        // Values count
    public int AvatarIndex;      // Avatar index
    public byte[] AvatarName = new byte[32];   // Avatar name
    public int FrameIndex;       // Frame data index
    public int Reserved1;         // Reserved, only enable this package has 64bytes length
    public int Reserved2;        // Reserved, only enable this package has 64bytes length
    public int Reserved3;        // Reserved, only enable this package has 64bytes length
    public short Token2;           // Package end token: 0xEEFF

    //添加2个内部类，分别实现指针类型接口、值类型接口
    public static class ByReference extends BvhDataHeader implements Structure.ByReference {}
    public static class ByValue extends BvhDataHeader implements Structure.ByValue{}

}
