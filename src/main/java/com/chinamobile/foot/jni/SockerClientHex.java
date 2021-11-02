package com.chinamobile.foot.jni;

import com.chinamobile.foot.util.HexConvert;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class SockerClientHex {

    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static void main(String[] args){
        String server = "127.0.0.1";
        int port = 7001;
        //port = 8800;  //脑电端口

        //向server的port端口发出客户请求
        Socket socket = null;
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
        try
        {
            socket = new Socket(server, port);
            inputStream = socket.getInputStream();
            String info = "";
            bufferedInputStream = new BufferedInputStream(inputStream);
            dataInputStream = new DataInputStream(bufferedInputStream);
            //一次读取一个byte
            byte[] bytes = new byte[5];
            //注意,read()方法如果没有数据会一直阻塞,也就是永远不会等于-1,除非客户端调用close(),如果想在while循环外部获取数据则需要设定跳出条件
            while ((dataInputStream.read(bytes)) != -1)
            {
                //System.out.println("#####bytes="+bytes);
                String tempStr = HexConvert.BinaryToHexString(bytes);  //ByteArrayToHexStr(bytes)+" "
                System.out.println("#####tempStr="+tempStr);
                info  += tempStr;
                //返回下次调用可以不受阻塞地从此流读取或跳过的估计字节数,如果等于0则表示已经读完
                if (dataInputStream.available() == 0)
                {
                    System.out.println(">>>终端信息读取完毕,最后一位:"+tempStr);
                    break;
                }
            }
            System.out.println(">>>线程收到来自终端的信息:"+info);
            //关闭终端的输入流(不关闭服务端的输出流),此时m_socket虽然没有关闭,但是客户端已经不能再发送消息
            socket.shutdownInput();
            //解析终端的信息
            String responseStr = "Null...";
            //模拟业务处理
            Thread.sleep(10000);
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.write(responseStr);
            printWriter.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //关闭资源
        finally
        {
            System.out.println(">>>本次连接已断开\n");
            try
            {
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
                if (socket != null)
                    socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
