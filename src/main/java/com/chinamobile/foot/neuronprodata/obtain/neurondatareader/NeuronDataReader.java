package com.chinamobile.foot.neuronprodata.obtain.neurondatareader;

import com.chinamobile.foot.neuronprodata.obtain.bvhdata.impl.FrameDataReceivedImpl;
import com.chinamobile.foot.neuronprodata.obtain.calcdata.impl.CalculationDataReceivedImpl;
import com.chinamobile.foot.neuronprodata.obtain.entity.BoneDimension;
import com.chinamobile.foot.neuronprodata.obtain.socketstatus.impl.SocketStatusChangeImpl;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface NeuronDataReader extends Library {
    /**
     * DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径
     */
    NeuronDataReader INSTANCE = (NeuronDataReader) Native.load("NeuronDataReader", NeuronDataReader.class);

    /**
     * 注册BVH数据接收回调句柄 BRRegisterFrameDataCallback<br/>
     * Parameters
     * customedObj User defined object.
     * handle A function pointer of FrameDataReceived type.
     * Remarks The handle of FrameDataReceived type points to the function address of the client.
     */
    void BRRegisterFrameDataCallback(Pointer customedObj, FrameDataReceivedImpl handle);

    /**
     * 回调中间数据 BRRegisterCalculationDataCallback
     *
     * @param customedObj User defined object.
     * @param handle      A function pointer of CalculationDataReceived type.
     *                    Remarks The handle of CalculationDataReceived type points to the function address of the client.
     */
    void BRRegisterCalculationDataCallback(Pointer customedObj, CalculationDataReceivedImpl handle);

    /**
     * 注册 socket 状态回调句柄。
     *
     * @param customedObj User defined object.
     * @param handle      A function pointer.
     *                    The handle of SocketStatusChanged type points to the function address of the client.
     */
    void BRRegisterSocketStatusCallback(Pointer customedObj, SocketStatusChangeImpl handle);

    /**
     * 由给定的IP地址和端口连接至服务器
     *
     * @param server
     * @param port
     * @return
     */
    Pointer BRConnectTo(String server, int port);

    /**
     * Start a UDP service to receive data at 'nPort'
     *
     * @param port
     * @return
     */
    Pointer BRStartUDPServiceAt(int port);

    /**
     * 由给定的 IP 地址和端口连接至服务器的数据通道。<br/>
     *
     * @param serverIP
     * @param nport
     * @return
     */
    Pointer BRConnectCmd(String serverIP, int nport);

    /**
     * 检查 socket 状态。 实际上通过 socket 回调句柄， 这个函数有相同的输出状态。 如果已经注册 socket 状
     * 态回调句柄， 这个函数就不需要使用了。
     *
     * @param sockRef
     * @return
     */
    int BRGetSocketStatus(Pointer sockRef);

    /**
     * 停止数据接收服务，需要在程序退出前调用这个函数来断开/停止服务器的服务，
     * 否则程序将由于数据接收线程的阻塞而不能退出
     *
     * @param sockRef
     */
    void BRCloseSocket(Pointer sockRef);

    /**
     * 一旦出现错误， 通过调用'BRGetLastErrorMessage'函数来获取错误信息。
     *
     * @return Return the last error message.
     * The error information can be acquired by calling 'BRGetLastErrorMessage' once error occurred during
     * function callback.
     */
    String BRGetLastErrorMessage();

    /**
     * 通过已经建立的命令通道向 Axis 发送命令。
     *
     * @param sockRef     a handle of connector [cmd].
     * @param avatarIndex avatar index in Axis.
     * @return If successfully, return true; otherwise false is returned.
     */
    boolean ZeroOut(Pointer sockRef, int avatarIndex);

    /**
     * 通过已经建立的命令通道向 Axis 发送命令。
     *
     * @param sockRef a handle of connector [cmd].
     * @return If successfully, return true; otherwise false is returned.
     */
    boolean ZeroOutAll(Pointer sockRef);

    /**
     * 通过已经建立的命令通道向 Axis 发送命令。
     *
     * @param sockRef a handle of connector [cmd].
     * @param order   bvh rotate order.
     * @return If successfully, return true and bvh rotate order; otherwise false is returned.
     */
    boolean GetBvhRotation(Pointer sockRef, int order);

    /**
     * 通过已经建立的命令通道向 Axis 发送命令。
     *
     * @param sockRef     a handle of connector [cmd]
     * @param avatarIndex avatar index in Axis.
     * @param dimensions  Bone dimensions, unit: meter, refer to BoneDimension define
     * @return If successfully, return true and Bone dimensions; otherwise false is returned
     */
    boolean GetBoneSize(Pointer sockRef, int avatarIndex, BoneDimension dimensions);

    /**
     * 通过已经建立的命令通道向 Axis 发送命令。
     *
     * @param sockRef     通过已经建立的命令通道向 Axis 发送命令
     * @param avatarIndex avatar index in Axis.
     * @param data        bvh header, refer to Appendix B.
     * @param len         length of data
     * @return If successfully, return true; otherwise false is returned.
     */
    boolean GetBvhHeader(Pointer sockRef, int avatarIndex, String data, int len);
}
