package com.chinamobile.foot.neuronprodata.calcdata;


import com.chinamobile.foot.neuronprodata.calcdata.entity.CalcDataHeader;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface CalculationDataReceived extends Callback {
    /**
     * 回调中间数据<br/>
     * typedef void(CALLBACK *FrameDataReceived)(void* customedObj,SOCKET_REF sender,BvhDataHeader* header,float* data);
     *
     * @param customedObj Userdefinedobject.
     * @param sender      Connector reference of TCP/IP client as identity.
     * @param header      CalcDataHeader type pointer,to output the calculation data format information.
     * @param data        Float type array pointer,to output binary data.
     *                    Remarks The related information of the data stream can be obtained from CalcDataHeader.
     */
    void invoke(Pointer customedObj, Pointer sender, CalcDataHeader header, float data);
}
