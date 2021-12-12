package com.chinamobile.digitaltwin.common.rxtx;

import com.chinamobile.digitaltwin.util.SerialTool;
import gnu.io.SerialPort;

import java.util.ArrayList;

public class RxtxMain {
    private static SerialTool serialTool = null;

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1(){
        ArrayList<String> portNameList = SerialTool.findPort();
        System.out.println("portNameList:::"+portNameList);
        SerialPort serialPort = null;
        for (String portName : portNameList) {
            try {
                serialPort = com.chinamobile.digitaltwin.util.SerialTool.openPort(portName, 9600);

                byte[] b = new byte[]{0x00, 0x5e};
                String s = "1";

                String str = SerialTool.readFromPort(serialPort);
                System.out.println(str);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                com.chinamobile.digitaltwin.util.SerialTool.closePort(serialPort);
            }
        }
    }

    public static void test2(){
        SerialPortServer sp = SerialPortServer.getInstall();
        ParamConfig paramConfig = new ParamConfig("COM3", 9600, 0, 8, 1);
        sp.init(paramConfig);
    }
}
