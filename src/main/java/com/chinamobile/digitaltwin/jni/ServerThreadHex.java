package com.chinamobile.digitaltwin.jni;

import com.chinamobile.digitaltwin.util.HexConvert;

import java.io.*;
import java.net.Socket;

public class ServerThreadHex extends Thread {

    private Socket m_socket;

    public ServerThreadHex(Socket socket) {
        this.m_socket = socket;
    }

    @Override
    public void run() {
        //字节输入流
        InputStream inputStream = null;
        //字节输出流
        OutputStream outputStream = null;
        //缓冲输入流
        BufferedInputStream bufferedInputStream = null;
        //数据输入流
        DataInputStream dataInputStream = null;
        //打印输出流
        PrintWriter printWriter = null;
        try {
            inputStream = m_socket.getInputStream();
            String info = "";
            bufferedInputStream = new BufferedInputStream(inputStream);
            dataInputStream = new DataInputStream(bufferedInputStream);
            //一次读取一个byte
            byte[] bytes = new byte[10240];
            //注意,read()方法如果没有数据会一直阻塞,也就是永远不会等于-1,除非客户端调用close(),如果想在while循环外部获取数据则需要设定跳出条件
            int count = 0;
            while ((dataInputStream.read(bytes)) != -1) {
                //System.out.println("#####bytes="+bytes);
                String tempStr = HexConvert.BinaryToHexString(bytes);  //ByteArrayToHexStr(bytes)+" "
                info += tempStr;
                count++;
                //返回下次调用可以不受阻塞地从此流读取或跳过的估计字节数,如果等于0则表示已经读完
                if (dataInputStream.available() == 0) {
                    System.out.println(">>>终端信息读取完毕,最后一位:" + tempStr);
                    break;
                }
            }
            System.out.println(">>>线程" + this.getId() + "收到来自终端的信息:" + info);
            System.out.println("info.len=" + info.length() + ",count=" + count);
            //关闭终端的输入流(不关闭服务端的输出流),此时m_socket虽然没有关闭,但是客户端已经不能再发送消息
            m_socket.shutdownInput();
            //解析终端的信息
            String responseStr = "Null...";
            //模拟业务处理
            Thread.sleep(10000);
            outputStream = m_socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.write(responseStr);
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭资源
        finally {
            System.out.println(">>>本次连接已断开\n");
            try {
                if (printWriter != null)
                    printWriter.close();
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
                if (bufferedInputStream != null)
                    bufferedInputStream.close();
                if (dataInputStream != null)
                    dataInputStream.close();
                if (m_socket != null)
                    m_socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 普通byte[]转16进制Str
     *
     * @param array
     */
    public static String ByteArrayToHexStr(byte[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            String hex = Integer.toHexString(array[i] & 0xFF);
            if (hex.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

}
