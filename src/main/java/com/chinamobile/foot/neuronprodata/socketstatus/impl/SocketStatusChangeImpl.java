package com.chinamobile.foot.neuronprodata.socketstatus.impl;

import com.chinamobile.foot.neuronprodata.socketstatus.SocketStatusChange;
import com.sun.jna.Pointer;

public class SocketStatusChangeImpl implements SocketStatusChange {
    private static int i = 1;

    /**
     * Socket 状态回调
     *
     * @param customedObj User defined object.
     * @param sender      Connector reference of TCP/IP client as identity
     * @param status      Indicate the status changes of current socket
     * @param message     Status description.
     */
    @Override
    public void invoke(Pointer customedObj, Pointer sender, int status, String message) {
        System.out.println("Invoke SocketStatusChangeImpl  successfully!#####,i=" + i++);
        System.out.println("sender:" + sender);
        System.out.println("status:" + status);
        System.out.println("message:" + message);
    }
}
