package com.chinamobile.foot.neurondata;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.FloatByReference;

public interface CalculationDataCallback extends Callback {
    /**
     * typedef void(CALLBACK *FrameDataReceived)(void* customedObj,SOCKET_REF sender,BvhDataHeader* header,float* data);
     * @param customedObj  Userdefinedobject.
     * @param sender   Connector reference of TCP/IP client as identity.
     * @param header   CalcDataHeader type pointer,to output the calculation data format information.
     * @param data   Float type array pointer,to output binary data.
     * Remarks The related information of the data stream can be obtained from CalcDataHeader.
     */
    void CalculationDataReceived(Pointer customedObj, Pointer sender, CalcDataHeader header, FloatByReference data);
}
