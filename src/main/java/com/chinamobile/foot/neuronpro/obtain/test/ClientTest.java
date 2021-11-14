package com.chinamobile.foot.neuronpro.obtain.test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientTest {
    public static final String IP_ADDR = "127.0.0.1";//服务器地址
    public static final int PORT = 7001;//服务器端口号
    static String text = null;

    public static void main(String[] args) throws IOException {
        System.out.println("客户端启动...");
        Socket socket = new Socket(IP_ADDR, PORT);

        while (true) {
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                //读取服务器端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                byte[] buffer;
                buffer = new byte[input.available()];
                if (buffer.length != 0) {
                    // 读取缓冲区
                    input.read(buffer);
                    // 转换字符串
                    String three = new String(buffer, "utf-8");
                    System.out.println("内容=" + three);
                }
            } catch (Exception e) {
                System.out.println("客户端异常:" + e.getMessage());
            }
        }
    }
}
