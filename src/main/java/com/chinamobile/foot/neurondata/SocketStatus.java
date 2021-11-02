package com.chinamobile.foot.neurondata;

public interface SocketStatus {
    public static final int CS_Running = 0;  //Socket is working correctly
    public static final int CS_Starting = 1; //Is trying to start service
    public static final int CS_OffWork = 2;  //Not working
}
