package com.chinamobile.foot.serialport;

import com.chinamobile.foot.util.SerialTool;
import com.chinamobile.foot.util.ThreadPoolUtil;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class SerialServer {

    public static void getSerialPortData(){
        ArrayList<String> portNameList = SerialTool.findPort();
        Map<String, SerialPort> portMap = new HashMap<>();

        //SerialPort serialPort = null;
        //循环监听串口
        while(true){
            for (String portName : portNameList) {
                if(!portName.equals("COM3")){
                    continue;
                }

                try {
                    SerialPort serialPort = SerialTool.openPort(portName, 9600);

                    /*SerialPort serialPort = portMap.get(portName);
                    if(serialPort == null){
                        serialPort = SerialTool.openPort(portName, 9600);
                        portMap.put(portName, serialPort);
                    }*/


                    //启动线程
                    //SerialThread serialThread = new SerialThread(serialPort);
                    //serialThread.start();

                    //final SerialPort serialPort1 = serialPort;
                    ExecutorService threadPool = ThreadPoolUtil.getIntance();
                    threadPool.submit(new Runnable() {

                        @SneakyThrows
                        @Override
                        public void run() {
                            //SerialPort serialPort = SerialTool.openPort(portName, 9600);

                            String str = SerialTool.readFromPort(serialPort);
                            //System.out.println("*****SerialServer.readFromPort="+str);
                            /*String[] arr = str.split("\r\n");
                            for (String s : arr) {
                                System.out.println("###SerialServer.readFromPort.str="+s);
                            }*/

                            //arr = null;
                            //str = null;
                        }
                    });

                    //SerialSendTread serialSendTread = new SerialSendTread(serialPort);
                    //serialSendTread.start();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args){
        getSerialPortData();
    }
}
