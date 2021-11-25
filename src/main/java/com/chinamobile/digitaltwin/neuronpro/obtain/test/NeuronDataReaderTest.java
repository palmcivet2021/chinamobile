package com.chinamobile.digitaltwin.neuronpro.obtain.test;

import com.chinamobile.digitaltwin.neuronpro.obtain.bvhdata.impl.FrameDataReceivedImpl;
import com.chinamobile.digitaltwin.neuronpro.obtain.neurondatareader.NeuronDataReader;
import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.List;

public class NeuronDataReaderTest {
    public static List<float[]> bvhDataList = new ArrayList();

    public static void main(String[] args) {
        String server = "127.0.0.1";
        int bvhPort = 7001;
        int calPort = 7003;

        /**
         * 获取BVH数据<br/>
         */
//        Thread socketStatusThread = new Thread(() -> {
//            Pointer socket = NeuronDataReader.INSTANCE.BRConnectTo(server, bvhPort);
//            SocketStatusChangeImpl socketStatusCallback = new SocketStatusChangeImpl();
//            NeuronDataReader.INSTANCE.BRRegisterSocketStatusCallback(null, socketStatusCallback);
//            synchronized (socketStatusCallback) {
//                try {
//                    System.out.println("进入socketStatusCallback阻塞,等待数据中...");
//                    socketStatusCallback.wait();
//                    System.out.println("bvhDataCallback阻塞完成");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        socketStatusThread.start();

        /**
         * 获取BVH数据<br/>
         */
        Thread bvhThread = new Thread(() -> {
            Pointer socket = NeuronDataReader.INSTANCE.BRConnectTo(server, bvhPort);
            FrameDataReceivedImpl bvhDataCallback = new FrameDataReceivedImpl();
            NeuronDataReader.INSTANCE.BRRegisterFrameDataCallback(null, bvhDataCallback);
            synchronized (bvhDataCallback) {
                try {
                    System.out.println("进入bvhDataCallback阻塞,等待数据中...");
                    bvhDataCallback.wait();
                    System.out.println("bvhDataCallback阻塞完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        bvhThread.start();

        /**
         * 获取中间数据<br/>
         */
//        Thread calulationThread = new Thread(() -> {
//            //回调中间数据
//            Pointer socket = NeuronDataReader.INSTANCE.BRConnectTo(server, calPort);
//            CalculationDataReceivedImpl calulationDataCallback = new CalculationDataReceivedImpl();
//            NeuronDataReader.INSTANCE.BRRegisterCalculationDataCallback(null, calulationDataCallback);
//            synchronized (calulationDataCallback) {
//                try {
//                    System.out.println("进入calulationDataCallback阻塞,等待数据中...");
//                    calulationDataCallback.wait();
//                    System.out.println("calulationDataCallback阻塞完成");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        calulationThread.start();
    }
}
