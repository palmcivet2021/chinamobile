package com.chinamobile.foot.neurondata;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.FloatByReference;

/**
 * 回调BVH数据
 */
public interface BvhDataCallback extends Callback {
    //invoke对应FrameDataReceived，注意参数顺序需要保持一致

    /**
     * typedef void(CALLBACK *FrameDataReceived)(void* customedObj,SOCKET_REF sender,BvhDataHeader* header,float* data);
     * @param customedObj  Userdefinedobject.
     * @param sender   Connector reference of TCP/IP client as identity.
     * @param header   BvhDataHeader type pointer,to output the BVH data format information.
     * @param data   Float type array pointer,to output binary data.
     * Remarks The related information of the data stream can be obtained from BvhDataHeader.
     */
    void FrameDataReceived(Pointer customedObj, Pointer sender, BvhDataHeader header, FloatByReference data);
}
