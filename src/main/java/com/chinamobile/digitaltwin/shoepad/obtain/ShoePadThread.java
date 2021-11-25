package com.chinamobile.digitaltwin.shoepad.obtain;

import com.chinamobile.digitaltwin.shoepad.bean.ShoePadData;
import com.chinamobile.digitaltwin.util.ThreadPoolUtil;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ShoePadThread extends Thread {
    private Socket m_socket;

    public ShoePadThread(Socket socket){
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

        try{
            // 从服务端程序接收数据
            inputStream = m_socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            //bufferedInputStream = new BufferedInputStream(inputStream);
            //dataInputStream = new DataInputStream(bufferedInputStream);
            //一次读取一个byte
            //byte[] bytes = new byte[1];
            String tempStr = "";
            while((tempStr = br.readLine()) != null){
                System.out.println(">>>ShoePad线程"+this.getId()+"收到来自终端的信息0:"+tempStr);
                dealShoePadData(tempStr);  //处理接收数据
            }


            /*while ((dataInputStream.read(bytes)) != -1){
                tempStr = new String(bytes);
                System.out.println(">>>ShoePad线程"+this.getId()+"收到来自终端的信息0:"+tempStr);
            }*/
            System.out.println("###ShoePad线程"+this.getId()+"收到来自终端的信息:"+tempStr);

            m_socket.shutdownInput();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭资源
        finally{
            System.out.println(">>>本次连接已断开\n");
            try{
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
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public void dealShoePadData(String str) {
        ExecutorService threadPool = ThreadPoolUtil.getIntance();
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                List<ShoePadData> dataList = DealShoePadData.dealData(str);
            }
        });
    }


}
