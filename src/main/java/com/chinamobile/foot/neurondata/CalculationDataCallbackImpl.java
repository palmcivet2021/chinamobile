package com.chinamobile.foot.neurondata;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.FloatByReference;

public class CalculationDataCallbackImpl implements CalculationDataCallback {
    private static int i = 1;

    @Override
    public void CalculationDataReceived(Pointer customedObj, Pointer sender, CalcDataHeader header, FloatByReference data) {
        System.out.printf("*******Invoke CalculationDataCallback  successfully!******,i="+i++);
        System.out.println("customedObj="+customedObj);
        System.out.println("sender="+sender);
        System.out.println("cal.header="+header+",name="+new String(header.AvatarName));
        System.out.println("cal.data="+data.getValue());
    }
}
