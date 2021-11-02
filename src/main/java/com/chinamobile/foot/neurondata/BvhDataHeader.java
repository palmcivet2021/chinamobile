package com.chinamobile.foot.neurondata;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * BVH数据流的头
 */
public class BvhDataHeader extends Structure {
    public short Token1;           // Package start token: 0xDDFF
    public DATA_VER DataVersion;      // Version of community data format. e.g.: 1.0.0.2
    public short DataCount;        // Values count
    public byte WithDisp;         // With/out displacement
    public byte WithReference;    // With/out reference bone data at first
    public int AvatarIndex;      // Avatar index
    public byte[] AvatarName = new byte[32];   // Avatar name
    public int FrameIndex;       // Frame data index
    public int Reserved;         // Reserved, only enable this package has 64bytes length
    public int Reserved1;        // Reserved, only enable this package has 64bytes length
    public int Reserved2;        // Reserved, only enable this package has 64bytes length
    public short Token2;           // Package end token: 0xEEFF

    public BvhDataHeader() {
        super();
    }

    public BvhDataHeader(Pointer _bvhDataHeader) {
        super(_bvhDataHeader);
    }

    //添加2个内部类，分别实现指针类型接口、值类型接口
    public static class ByReference extends BvhDataHeader implements Structure.ByReference {}
    public static class ByValue extends BvhDataHeader implements Structure.ByValue{}

    //定义取值次序，需要与C/C++中对齐，不然会出现NoSuchFieldError
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[]{"Token1", "DataVersion", "DataCount", "WithDisp", "WithReference", "AvatarIndex","AvatarName", "FrameIndex", "Reserved", "Reserved1", "Reserved2", "Token2"});
    }

}
