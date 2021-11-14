package com.chinamobile.foot.neuronpro.obtain.socketstatus;


import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * 回调BVH数据
 */
public interface SocketStatusChange extends Callback {

    /**
     * Socket 状态回调
     *
     * @param customedObj User defined object.
     * @param sender      Connector reference of TCP/IP client as identity
     * @param status      Indicate the status changes of current socket
     * @param message     Status description.
     */
    void invoke(Pointer customedObj, Pointer sender, int status, String message);
}
