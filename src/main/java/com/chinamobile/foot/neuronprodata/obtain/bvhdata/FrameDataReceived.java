package com.chinamobile.foot.neuronprodata.obtain.bvhdata;


import com.chinamobile.foot.neuronprodata.obtain.bvhdata.entity.BvhDataHeader;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;

/**
 * 回调BVH数据
 */
public interface FrameDataReceived extends Callback {
    /**
     * 回调BVH数据<br/>
     * typedef void(CALLBACK *FrameDataReceived)(void* customedObj,SOCKET_REF sender,BvhDataHeader* header,float* data);
     *
     * @param customedObj User defined object.
     * @param sender      Connector reference of TCP/IP client as identity.
     * @param header      BvhDataHeader type pointer,to output the BVH data format information.
     * @param data        Float type array pointer,to output binary data.
     *                    Remarks The related information of the data stream can be obtained from BvhDataHeader.
     */
    void invoke(Pointer customedObj, Pointer sender, BvhDataHeader.ByReference header, FloatByReference data);
}
