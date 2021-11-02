package com.chinamobile.foot.neurondata;

import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.List;

public class NeuronDataReaderTest {
    public static List<Float> bvhDataList = new ArrayList();

    public static void main(String[] args){
        String server = "127.0.0.1";
        int BVHPort = 7001;
        int CalPort = 7003;

        //Pointer socket = NeuronDataReaderDllLib.INSTANCE.BRConnectTo(server, port);
        //System.out.println(socket);

        Pointer customedObj = null;
        //BvhDataCallbackImpl bvhDataCallback = new BvhDataCallbackImpl();
        //NeuronDataReaderDllLib.INSTANCE.BRRegisterFrameDataCallback(customedObj, bvhDataCallback);

        Thread thread1 = new Thread(()->{
            Pointer socket = NeuronDataReaderDllLib.INSTANCE.BRConnectTo(server, BVHPort);
            System.out.println(socket);

            BvhDataCallbackImpl bvhDataCallback = new BvhDataCallbackImpl();
            NeuronDataReaderDllLib.INSTANCE.BRRegisterFrameDataCallback(null, bvhDataCallback);

            synchronized (bvhDataCallback){
                try {
                    System.out.println("进入bvhDataCallback阻塞,等待数据中...");
                    bvhDataCallback.wait();
                    System.out.println("bvhDataCallback阻塞完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        thread1.start();



        Thread thread2 = new Thread(()->{
            Pointer socket = NeuronDataReaderDllLib.INSTANCE.BRConnectTo(server, CalPort);
            System.out.println(socket);

            CalculationDataCallbackImpl calculationDataCallback = new CalculationDataCallbackImpl();
            NeuronDataReaderDllLib.INSTANCE.BRRegisterCalculationDataCallback(null, calculationDataCallback);
            synchronized (calculationDataCallback){
                try {
                    System.out.println("进入calculationDataCallback阻塞,等待数据中...");
                    calculationDataCallback.wait();
                    System.out.println("calculationDataCallback阻塞完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }




        });
        thread2.start();

        //NeuronDataReaderDllLib.INSTANCE.BRRegisterCalculationDataCallback(customedObj, new CalculationDataCallbackImpl());

        //List<Float> bvhDataList = bvhDataCallback.getBvhDataList();
        for (Float bvhData : bvhDataList) {
            System.out.println(bvhData);
        }
    }
}
