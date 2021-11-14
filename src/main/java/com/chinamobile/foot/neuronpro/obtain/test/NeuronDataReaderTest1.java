package com.chinamobile.foot.neuronpro.obtain.test;

import com.chinamobile.foot.neuronpro.obtain.bvhdata.impl.FrameDataReceivedImpl;
import com.chinamobile.foot.neuronpro.obtain.calcdata.impl.CalculationDataReceivedImpl;
import com.chinamobile.foot.neuronpro.obtain.neurondatareader.NeuronDataReader;
import com.sun.jna.Pointer;

public class NeuronDataReaderTest1 {
    public static void main(String[] args) {
        new BvhDataThread().start();
        //new CalulationDataThread().start();
        //new CmdThread().start();
    }
}

class BvhDataThread extends Thread {
    public void run() {
        String server = "127.0.0.1";
        int bvhPort = 7001;
        Pointer socket = NeuronDataReader.INSTANCE.BRConnectTo(server, bvhPort);
        while (true) {
            FrameDataReceivedImpl bvhDataCallback = new FrameDataReceivedImpl();
            NeuronDataReader.INSTANCE.BRRegisterFrameDataCallback(socket, bvhDataCallback);
        }
    }
}

class CalulationDataThread extends Thread {
    public void run() {
        String server = "127.0.0.1";
        int calPort = 7003;
        Pointer socket = NeuronDataReader.INSTANCE.BRConnectTo(server, calPort);
        while (true) {
            CalculationDataReceivedImpl calulationDataCallback = new CalculationDataReceivedImpl();
            NeuronDataReader.INSTANCE.BRRegisterCalculationDataCallback(null, calulationDataCallback);
        }
    }
}

//class CmdThread extends Thread {
//    public void run() {
//        String serverIP = "127.0.0.1";
//        int nPort = 8013;
//        Pointer socket = NeuronDataReader.INSTANCE.BRConnectCmd(serverIP, nPort);
//        boolean zeroOut = NeuronDataReader.INSTANCE.ZeroOut(socket, 256);
//        System.out.println("CmdThread zeroOut:" + zeroOut);
//    }
//}
