package com.chinamobile.foot.eheaddata.obtain;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EheadServer {
    private static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);
    public volatile static boolean isStart = true;  //false
    public volatile static long startTime = System.currentTimeMillis();  //数据采集开始时间

    public static void getEHeadData() {
        try {
            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(9687);  //
            Socket socket = null;
            //记录客户端的数量
            int count = 0;
            System.out.println("***脑电数据接收服务即将启动，等待客户端的连接***");
            //循环监听等待客户端的连接
            while (true) {  //while(true)
                System.out.println("#########脑电数据接收服务启动############");
                //调用accept()方法开始监听，等待客户端的连接
                socket = serverSocket.accept();
                //创建一个新的线程
                //EheadThreadHex eheadThreadHex = new EheadThreadHex(socket, blockingQueue);
                EheadThreadHex eheadThreadHex = new EheadThreadHex(socket);
                //EheadThreadHex eheadThreadHex = new EheadThreadHex(socket, EheadServer.isStart);
                //启动线程
                eheadThreadHex.start();

                //启动处理数据线程
                //DealDataThread dealDataThread = new DealDataThread(blockingQueue);
                //dealDataThread.start();

                count++;//统计客户端的数量
                System.out.println("客户端的数量：" + count);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP：" + address.getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setStart(boolean isStart) {
        isStart = isStart;
    }

    public static void main(String[] args) {
        EheadServer.getEHeadData();
    }

}
