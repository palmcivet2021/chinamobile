package com.chinamobile.foot.serialport;

import com.chinamobile.foot.util.SerialTool;
import gnu.io.SerialPort;

public class SerialThread extends Thread{
    SerialPort serialPort = null;

    public SerialThread(SerialPort serialPort){
        this.serialPort = serialPort;
    }

    //线程执行的操作
    @Override
    public void run(){
        try {
            /*byte[] bytes = SerialTool.readFromPort(serialPort);
            if(bytes != null) {
                System.out.println("####Receive From "+serialPort.getName() + ",bytes=" + bytes);
            }*/

            String str = SerialTool.readFromPort(serialPort);
            System.out.println("*****SerialThread.str="+str);
            String[] arr = str.split("\r\n");
            for (String s : arr) {
                System.out.println("###SerialThreadevery str="+s);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            SerialTool.closePort(serialPort);
        }


    }
}
