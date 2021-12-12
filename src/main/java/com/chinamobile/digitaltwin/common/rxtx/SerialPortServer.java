package com.chinamobile.digitaltwin.common.rxtx;

import com.alibaba.fastjson.JSON;
import com.chinamobile.digitaltwin.shoepad.bean.ShoePadData;
import com.chinamobile.digitaltwin.util.FileUtil;
import com.chinamobile.digitaltwin.util.ThreadPoolUtil;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;

public class SerialPortServer implements SerialPortEventListener {
    // 检测系统中可用的通讯端口类
    private CommPortIdentifier commPortId;
    // 枚举类型
    private Enumeration<CommPortIdentifier> portList;
    // RS232串口
    private SerialPort serialPort;
    // 输入流
    private InputStream in;
    // 输出流
    private OutputStream out;
    // 保存串口返回信息
    private String data;
    // 保存串口返回信息十六进制
    private String dataHex;
    private static SerialPortServer spServer=null;
    private List<ShoePadData> dataList = new LinkedList<>();

    private SerialPortServer(){}

    public static SerialPortServer getInstall(){
        if(spServer == null){
            synchronized (SerialPortServer.class){
                if(spServer == null){
                    spServer = new SerialPortServer();
                }
            }
        }
        return spServer;
    }

    public void init(ParamConfig paramConfig) {
        // 获取系统中所有的通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        // 记录是否含有指定串口
        boolean isExsist = false;
        // 循环通讯端口
        while (portList.hasMoreElements()) {
            commPortId = portList.nextElement();
            // 判断是否是串口
            if (commPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // 比较串口名称是否是指定串口
                if (paramConfig.getSerialNumber().equals(commPortId.getName())) {
                    // 串口存在
                    isExsist = true;

                    try {
                        // open:（应用程序名[随意命名]，阻塞时等待的毫秒数）
                        serialPort = (SerialPort) commPortId.open(Object.class.getSimpleName(), 2000);
                        // 设置串口监听
                        serialPort.addEventListener(this);
                        // 设置串口数据时间有效(可监听)
                        serialPort.notifyOnDataAvailable(true);
                        // 设置串口通讯参数:波特率，数据位，停止位,校验方式
                        serialPort.setSerialPortParams(
                                paramConfig.getBaudRate(),
                                paramConfig.getDataBit(),
                                paramConfig.getStopBit(),
                                paramConfig.getCheckoutBit());
                        System.out.println(commPortId.getName()+"端口连接成功\n");
                    } catch (PortInUseException e) {
                        //throw new CustomException("端口被占用");
                    } catch (TooManyListenersException e) {
                        //throw new CustomException("监听器过多");
                    } catch (UnsupportedCommOperationException e) {
                        //throw new CustomException("不支持的COMM端口操作异常");
                    }
                    // 结束循环
                    break;
                }
            }
        }
        // 若不存在该串口则抛出异常
        if (!isExsist) {
            //throw new CustomException("不存在该串口！");
            System.out.println("不存在指定的串口");
        }
    }


    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI:    // 通讯中断
            case SerialPortEvent.OE:    // 溢位错误
            case SerialPortEvent.FE:    // 帧错误
            case SerialPortEvent.PE:    // 奇偶校验错误
            case SerialPortEvent.CD:    // 载波检测
            case SerialPortEvent.CTS:   // 清除发送
            case SerialPortEvent.DSR:   // 数据设备准备好
            case SerialPortEvent.RI:    // 响铃侦测
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 有数据到达
                String data = readComm();
                System.out.println(data);
                //dataHandler(data);
                break;
            default:
                break;
        }
    }

    private void dataHandler(String data) {
        final String[] arr = data.split("\r\n");
        for (String line : arr) {
            int lineLen = "1497,1690,1564,1742,1555,1661,1676,1746#0.127,1.018,-0.087#0.000,0.000,0.000#95.054,-7.059,-3.455# -47,-297,-116".length();
            if (line.length() < lineLen) continue;
            ShoePadData obj = converToShoePadData(line);
            if (obj != null) {
                dataList.add(obj);
                //System.out.println("接收到数据的条数"+dataList.size());
            }
        }
        if(dataList.size() >= 1000) {
            List<ShoePadData> allList = new LinkedList<>();
            allList.addAll(dataList);
            dataList.clear();

            ExecutorService threadPool = ThreadPoolUtil.getIntance();
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    //保存集合数据到json文件
                    String jsonString = JSON.toJSONString(allList);
                    System.out.println("jsonString::" + jsonString);
                    FileUtil.createJsonFile(jsonString, "d:/json/", System.currentTimeMillis()+"");
                }
            });

        }
    }

    public String readComm() {
        //此处代码做线程休眠，是由于串口数据读取大快不能完整获取到一条数据
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();
        try {
            in = serialPort.getInputStream();
            // 通过输入流对象的available方法获取数组字节长度
            byte[] readBuffer = new byte[in.available()];
            // 从输入流读取数据的长度
            int len = 0;

            while ((len = in.read(readBuffer)) != -1) {
                // 直接获取到的数据
                data = new String(readBuffer, 0, len).trim();
                sb.append(data);
//                System.out.println("data::\n" + sb.toString());
                in.close();
                in = null;
                break;
            }

        } catch (IOException e) {
            //throw new CustomException("读取串口数据时发生IO异常");
        }
        return sb.toString();
    }

    /**
     * 通过串口发送数据
     * @param data
     */
    public void sendComm(String data) {
        byte[] writerBuffer = null;
        try {
            //以十六进制数据发送
            writerBuffer = hexToByteArray(data);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            out = serialPort.getOutputStream();
            out.write(writerBuffer);
            out.flush();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeSerialPort();
        }
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        if (serialPort != null) {
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    System.out.println("关闭输入流时发生IO异常");
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    System.out.println("关闭输出流时发生IO异常");
                    e.printStackTrace();
                }
            }
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * 十六进制串口返回值获取
     */
    public String getDataHex() {
        String result = dataHex;
        // 置空执行结果
        dataHex = null;
        // 返回执行结果
        return result;
    }

    /**
     * 串口返回值获取
     */
    public String getData() {
        String result = data;
        // 置空执行结果
        data = null;
        // 返回执行结果
        return result;
    }

    /**
     * Hex字符串转byte
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * hex字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * byte数组转换成十六进制字符串
     * @param bytes
     * @return
     */
    public static final String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2) sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static ShoePadData converToShoePadData(String str){
        ShoePadData data = new ShoePadData();
        String[] arr = str.split("#");
        //ad1~ad8压力传感器读数，共八组数据
        String[] ads = arr[0].split(",");
        data.setAd1(Integer.parseInt(ads[0]));
        data.setAd2(Integer.parseInt(ads[1]));
        data.setAd3(Integer.parseInt(ads[2]));
        data.setAd4(Integer.parseInt(ads[3]));
        data.setAd5(Integer.parseInt(ads[4]));
        data.setAd6(Integer.parseInt(ads[5]));
        data.setAd7(Integer.parseInt(ads[6]));
        data.setAd8(Integer.parseInt(ads[7]));
        //加速度，共x,y,x三组数据
        String[] accelerations = arr[1].split(",");
        data.setAcceleration_x(Float.parseFloat(accelerations[0]));
        data.setAcceleration_y(Float.parseFloat(accelerations[1]));
        data.setAcceleration_z(Float.parseFloat(accelerations[2]));
        //角速度，共x,y,x三组数据
        String[] angular_speeds = arr[2].split(",");
        data.setAngular_speed_x(Float.parseFloat(angular_speeds[0]));
        data.setAngular_speed_y(Float.parseFloat(angular_speeds[1]));
        data.setAngular_speed_z(Float.parseFloat(angular_speeds[2]));
        //角度，共x,y,x三组数据
        String[] angles = arr[3].split(",");
        data.setAngle_x(Float.parseFloat(angles[0]));
        data.setAngle_y(Float.parseFloat(angles[1]));
        data.setAngle_z(Float.parseFloat(angles[2]));
        //磁场强度，共x,y,x三组数据
        String[] magnetic_strengths = arr[4].split(",");
        data.setMagnetic_strength_x(Float.parseFloat(magnetic_strengths[0]));
        data.setMagnetic_strength_y(Float.parseFloat(magnetic_strengths[1]));
        data.setMagnetic_strength_z(Float.parseFloat(magnetic_strengths[2]));

        data.setDeal_time(new Long(System.currentTimeMillis() / 1000).intValue());

        return data;
    }
}
