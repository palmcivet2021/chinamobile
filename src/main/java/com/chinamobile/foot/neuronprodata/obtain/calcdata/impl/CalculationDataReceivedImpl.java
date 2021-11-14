package com.chinamobile.foot.neuronprodata.obtain.calcdata.impl;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.foot.neuronprodata.obtain.calcdata.CalculationDataReceived;
import com.chinamobile.foot.neuronprodata.obtain.calcdata.entity.CalcDataHeader;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;

public class CalculationDataReceivedImpl implements CalculationDataReceived {
    private static int i = 1;

    /**
     * 回调中间数据<br/>
     *
     * @param customedObj Userdefinedobject.
     * @param sender      Connector reference of TCP/IP client as identity.
     * @param header      CalcDataHeader type pointer,to output the calculation data format information.
     * @param data        Float type array pointer,to output binary data.
     */
    public void invoke(Pointer customedObj, Pointer sender, CalcDataHeader.ByReference header, FloatByReference data) {
        System.out.printf("*******Invoke CalculationDataReceivedImpl  successfully!******,i=" + i++);
        System.out.println("sender:" + sender);
        System.out.println("header:" + JSONObject.toJSONString(header));
        System.out.println("data:" + data.getValue());
    }
}
