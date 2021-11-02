package com.chinamobile.foot.neurondata;

import com.chinamobile.foot.jni.TestDll;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface NeuronDataReaderDllLib extends Library {
    // DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径
    NeuronDataReaderDllLib INSTANCE = (NeuronDataReaderDllLib) Native.load("NeuronDataReader", NeuronDataReaderDllLib.class);

    // 由给定的IP地址和端口连接至服务器
    Pointer BRConnectTo(String server, int port);

    //Start a UDP service to receive data at 'nPort'
    Pointer BRStartUDPServiceAt(int port);

    //停止数据接收服务，需要在程序退出前调用这个函数来断开/停止服务器的服务，
    // 否则程序将由于数据接收线程的阻塞而不能退出
    void BRCloseSocket(Pointer sockRef);

    //检查socket状态。如果已经注册socket状态回调句柄，这个函数就不需要使用了
    SocketStatus BRGetSocketStatus(Pointer sockRef);


    /**
     * 回调函数和注册回调
     * 通过回调函数，NeuronDataReader库输出骨骼数据或者socket状态。
     * 因此，当接收这些数据时，应该先注册NeuronDataReader库中相关的回调句柄。
     */
    //1.回调BVH数据 BRRegisterFrameDataCallback
    void BRRegisterFrameDataCallback(Pointer customedObj, Callback callback);

    //2.回调中间数据 BRRegisterCalculationDataCallback
    void BRRegisterCalculationDataCallback(Pointer customedObj, Callback callback);
}
