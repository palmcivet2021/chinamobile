package com.chinamobile.digitaltwin.neuronpro.obtain;

import java.util.concurrent.LinkedBlockingDeque;

public class NettyNeuronTest {
    private static final int CAPACITY = 100;

    public static void main(String[] args) {
        LinkedBlockingDeque<String> blockingQueue = new LinkedBlockingDeque<String>(CAPACITY);

        //1.接收动捕设备数据
        Thread thread1 = new Thread(() -> {
            //接收设备数据
            try {
                recive(blockingQueue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        //2.把接收的动捕设备数据发给前端
        Thread thread2 = new Thread(() -> {
            try {
                send(blockingQueue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread2.start();
    }

    /**
     * 接收动捕设备数据<br/>
     *
     * @param blockingQueue
     * @throws Exception
     */
    private static void recive(LinkedBlockingDeque<String> blockingQueue) {
        NettyNeuronClient client = new NettyNeuronClient("P-1", blockingQueue);
        //String ip = "114.67.239.45";
        String ip = "2.0.1.42";
        int port = 7001;
        client.startObtainData(ip, port);
    }

    /**
     * 发送设备数据给前端<br/>
     *
     * @param blockingQueue
     * @throws Exception
     */
    private static void send(LinkedBlockingDeque<String> blockingQueue) {
        //发送设备数据给前端
        NeuronServer neuronServer = new NeuronServer("C-1", blockingQueue);
        neuronServer.sendNeuronData();
    }
}
