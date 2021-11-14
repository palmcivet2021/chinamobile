package com.chinamobile.foot.neuronprodata.obtain.bvhdata.impl;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.foot.neuronprodata.obtain.bvhdata.FrameDataReceived;
import com.chinamobile.foot.neuronprodata.obtain.bvhdata.entity.BvhDataHeader;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;

public class FrameDataReceivedImpl implements FrameDataReceived {
    private static int i = 1;

    /**
     * 回调BVH数据<br/>
     *
     * @param customedObj Userdefinedobject.
     * @param sender      Connector reference of TCP/IP client as identity.
     * @param header      BvhDataHeader type pointer,to output the BVH data format information.
     * @param data        Float type array pointer,to output binary data.
     */
    @Override
    public void invoke(Pointer customedObj, Pointer sender, BvhDataHeader.ByReference header, FloatByReference data) {
        System.out.println("Invoke BvhDataCallback  successfully!#####,i=" + i++);
        System.out.println("sender:" + sender);
        System.out.println("header:" + JSONObject.toJSONString(header));
        System.out.println("data:" + data.getValue());
    }
}
