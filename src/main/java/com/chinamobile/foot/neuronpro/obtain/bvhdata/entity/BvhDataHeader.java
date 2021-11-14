package com.chinamobile.foot.neuronpro.obtain.bvhdata.entity;


import com.chinamobile.foot.neuronpro.obtain.entity.DATA_VER;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * BVH数据流的头
 */
public class BvhDataHeader extends Structure {
    // Package start token: 0xDDFF
    public short Token1;
    // Version of community data format. e.g.: 1.0.0.2
    public DATA_VER DataVersion;
    // Values count 数据的个数
    public short DataCount;
    // With/out displacement 是否带位移
    public boolean WithDisp;
    // With/out reference bone data at first
    public boolean WithReference;
    // Avatar index
    public int AvatarIndex;
    // Avatar name
    public byte[] AvatarName = new byte[32];
    // Frame data index
    public int FrameIndex;
    // Reserved, only enable this package has 64bytes length
    public int Reserved;
    // Reserved, only enable this package has 64bytes length
    public int Reserved1;
    // Reserved, only enable this package has 64bytes length
    public int Reserved2;
    // Package end token: 0xEEFF
    public short Token2;

    public static class ByReference extends BvhDataHeader implements Structure.ByReference {
    }

    public static class ByValue extends BvhDataHeader implements Structure.ByValue {
    }

    //定义取值次序，需要与C/C++中对齐，不然会出现NoSuchFieldError
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[]{"Token1", "DataVersion", "DataCount", "WithDisp",
                "WithReference", "AvatarIndex", "AvatarName", "FrameIndex", "Reserved", "Reserved1", "Reserved2", "Token2"});
    }
}
