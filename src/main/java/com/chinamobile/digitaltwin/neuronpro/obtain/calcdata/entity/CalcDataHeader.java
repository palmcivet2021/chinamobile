package com.chinamobile.digitaltwin.neuronpro.obtain.calcdata.entity;


import com.chinamobile.digitaltwin.neuronpro.obtain.entity.DATA_VER;
import com.sun.jna.Structure;

/**
 * CalcData数据流头
 */
@Structure.FieldOrder({"Token1", "DataVersion", "DataCount", "AvatarIndex", "AvatarName", "FrameIndex", "Reserved1", "Reserved2", "Reserved3", "Token2"})
public class CalcDataHeader extends Structure {
    // Package start token: 0xDDFF
    public short Token1;
    // Version of community data format. e.g.: 1.0.0.2
    public DATA_VER DataVersion;
    // Values count
    public short DataCount;
    // Avatar index
    public int AvatarIndex;
    // Avatar name
    public byte[] AvatarName = new byte[32];
    // Frame data index
    public int FrameIndex;
    // Reserved, only enable this package has 64bytes length
    public int Reserved1;
    // Reserved, only enable this package has 64bytes length
    public int Reserved2;
    // Reserved, only enable this package has 64bytes length
    public int Reserved3;
    // Package end token: 0xEEFF
    public short Token2;

    public static class ByReference extends CalcDataHeader implements Structure.ByReference {
    }

    public static class ByValue extends CalcDataHeader implements Structure.ByValue {
    }
}
