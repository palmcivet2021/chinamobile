package com.chinamobile.digitaltwin.serialport;

import com.chinamobile.digitaltwin.util.SerialTool;
import gnu.io.SerialPort;

public class SerialSendTread extends Thread {
    SerialPort serialPort = null;

    public SerialSendTread(SerialPort serialPort){
        this.serialPort = serialPort;
    }

    @Override
    public void run(){
        byte[] b = new byte[]{0x00, 0x5e};
        SerialTool.sendToPort(serialPort, b);
        System.out.println("####Send From "+serialPort.getName() + ",bytes=" + b);
        SerialTool.closePort(serialPort);
    }
}
