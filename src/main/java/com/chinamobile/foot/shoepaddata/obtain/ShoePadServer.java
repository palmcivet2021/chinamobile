package com.chinamobile.foot.shoepaddata.obtain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ShoePadServer {

    public static void getShoePadData(){
        try{
            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket=new ServerSocket(6789);  //
            Socket socket = null;
            //记录客户端的数量
            int count=0;
            System.out.println("***蓝牙鞋垫数据接收服务即将启动，等待客户端的连接***");
            //循环监听等待客户端的连接
            while (true){
                //调用accept()方法开始监听，等待客户端的连接
                socket = serverSocket.accept();
                //创建一个新的线程
                ShoePadThread shoePadThread = new ShoePadThread(socket);
                //启动线程
                shoePadThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ShoePadServer.getShoePadData();
    }


}
