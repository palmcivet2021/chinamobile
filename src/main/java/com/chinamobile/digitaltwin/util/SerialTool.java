package com.chinamobile.digitaltwin.util;

import com.chinamobile.digitaltwin.base.service.BaseDataService;
import com.chinamobile.digitaltwin.shoepad.bean.ShoePadData;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerialTool {
    private static BaseDataService baseDataService = SpringContextUtil.getBean(BaseDataService.class);
    private static SerialTool serialTool = null;
    private static Pattern r = null;
    private static List<ShoePadData> dataList = new ArrayList<>();

    static {
        //在该类被ClassLoader加载时就初始化一个SerialTool对象
        if (serialTool == null) {
            serialTool = new SerialTool();
        }

        String pattern = "ad1\\s=\\s\\d{3,4},\\sad2\\s=\\s\\d{3,4},\\sad3\\s=\\s\\d{3,4},\\sad4\\s=\\s\\d{3,4},\\sad5\\s=\\d{3,4},\\sad6\\s=\\s\\d{3,4},\\sad7\\s=\\s\\d{3,4},\\sda8\\s=\\s\\d{3,4}";
        r = Pattern.compile(pattern);
    }

    //私有化SerialTool类的构造方法，不允许其他类生成SerialTool对象
    private SerialTool() {}

    /**
     * 获取提供服务的SerialTool对象
     * @return serialTool
     */
    public static SerialTool getSerialTool() {
        if (serialTool == null) {
            serialTool = new SerialTool();
        }
        return serialTool;
    }


    /**
     * 查找所有可用端口
     * @return 可用端口名称列表
     */
    public static final ArrayList<String> findPort() {

        //获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();

        ArrayList<String> portNameList = new ArrayList<>();

        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }

        return portNameList;

    }

    /**
     * 打开串口
     * @param portName 端口名称
     * @param baudrate 波特率
     * @return 串口对象
     */
    public static final SerialPort openPort(String portName, int baudrate) throws NoSuchPortException, PortInUseException {
        SerialPort serialPort = null;
        CommPort commPort = null;
        try {

            //通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            if (portIdentifier.isCurrentlyOwned()){
                //System.out.println("opened");
            }
            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            //CommPort commPort = portIdentifier.open(portName, 2000);
            commPort = portIdentifier.open(portName, 2000);

            //判断是不是串口
            if (commPort instanceof SerialPort) {

                //SerialPort serialPort = (SerialPort) commPort;
                serialPort = (SerialPort) commPort;
                try {
                    //设置一下串口的波特率等参数
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                } catch (UnsupportedCommOperationException e) {
                    //throw new SerialPortParameterFailure();
                }

                //System.out.println("Open " + portName + " sucessfully !");
                return serialPort;

            }
            else {
                //不是串口
                //throw new NotASerialPortException();
            }
        } catch (NoSuchPortException e1) {
            throw new NoSuchPortException();
        } catch (PortInUseException e2) {
            if(serialPort != null) {
                SerialTool.closePort(serialPort);
            }
            throw new PortInUseException();
        }

        return null;
    }

    /**
     * 关闭串口
     * @param serialPort 待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * 往串口发送数据
     * @param serialPort 串口对象
     * @param order    待发送数据
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) {

        OutputStream out = null;

        try {

            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            //throw new SendDataToSerialPortFailure();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                //throw new SerialPortOutputStreamCloseFailure();
            }
        }

    }

    /**
     * 从串口读取数据
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static String readFromPort(SerialPort serialPort){

        InputStream in = null;
        byte[] bytes = null;
        StringBuilder sb = new StringBuilder();
        //List<String> strList = new ArrayList<>();
        Matcher m = null;

        try {
            in = serialPort.getInputStream();
            int bufflenth = in.available();        //获取buffer里的数据长度

            String str = null;
            String lastStr = "";
            String[] arr = null;
            //bufflenth = 12;

            while (bufflenth != 0) {
                System.out.println("########");
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度

                in.read(bytes);
                bufflenth = in.available();
                //System.out.println("bytes="+new String(bytes));
                str = new String(bytes);
                //sb.append(str);
                arr = str.split("\r\n");
                for (String line : arr) {
                    //strList.add(s);
                    m = r.matcher(line);
                    if(m.matches()){
                        //if(!line.equals(lastStr)) {
                            ShoePadData data = converToShoePadData(line);
                            if(data != null) {
                                dataList.add(data);
                            }
                        //}
                        //lastStr = line;

                        if(dataList.size() >= 100){
                            ExecutorService threadPool = ThreadPoolUtil.getIntance();
                            threadPool.submit(new Runnable() {
                                @Override
                                public void run() {
                                    List<ShoePadData> allList = new LinkedList<>();
                                    allList.addAll(dataList);
                                    dataList.clear();

                                    //saveToDb(allList);
                                    baseDataService.insertBatchData(allList);
                                }
                            });
                        }
                        System.out.println("###SerialTool.every str="+line);
                    }
                }
                Thread.sleep(1000);

                /*final byte[] b = bytes;
                Thread thread = new Thread(()->{
                    String s = new String(b);
                    System.out.println("*******="+s);
                });
                thread.start();*/

            }
            //Thread.sleep(10000);
            //saveData(strList);

        } catch (Exception e) {
            e.printStackTrace();
            //throw new ReadDataFromSerialPortFailure();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
                closePort(serialPort);
            } catch(IOException e) {
                //throw new SerialPortInputStreamCloseFailure();
            }

        }

        /*Thread thread = new Thread(()->{
            InputStream in = null;
            byte[] bytes = null;
            try {
                in = serialPort.getInputStream();
                int bufflenth = in.available();        //获取buffer里的数据长度

                String str = null;
                String[] arr = null;
                //bufflenth = 12;

                while (bufflenth != 0) {
                    bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度

                    in.read(bytes);
                    bufflenth = in.available();
                    //System.out.println("bytes="+new String(bytes));
                    str = new String(bytes);
                    arr = str.split("\r\n");
                    for (String s : arr) {
                        System.out.println("###every str="+s);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                //throw new ReadDataFromSerialPortFailure();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                        in = null;
                    }
                } catch(IOException e) {
                    //throw new SerialPortInputStreamCloseFailure();
                }

            }

        });
        thread.start();*/

        //return bytes;
        return sb.toString();

    }


    private static void saveData(List<String> dataList){
        for (String s : dataList) {
            System.out.println("###saveData.str="+s);
        }
        //dataList.clear();
    }

    /**
     * 添加监听器
     * @param port     串口对象
     * @param listener 串口监听器
     * @throws TooManyListenersException 监听类对象过多
     */
    public static void addListener(SerialPort port, SerialPortEventListener listener) throws TooManyListenersException {

        try {

            //给串口添加监听器
            port.addEventListener(listener);
            //设置当有数据到达时唤醒监听接收线程
            port.notifyOnDataAvailable(true);
            //设置当通信中断时唤醒中断线程
            port.notifyOnBreakInterrupt(true);

        } catch (TooManyListenersException e) {
            throw new TooManyListenersException();
        }
    }


    public static ShoePadData converToShoePadData(String str){
        ShoePadData data = new ShoePadData();
        String[] arr = str.split(",");
        data.setAd1(Integer.parseInt(arr[0].substring(arr[0].indexOf("=")+1).trim()));
        data.setAd2(Integer.parseInt(arr[1].substring(arr[1].indexOf("=")+1).trim()));
        data.setAd3(Integer.parseInt(arr[2].substring(arr[2].indexOf("=")+1).trim()));
        data.setAd4(Integer.parseInt(arr[3].substring(arr[3].indexOf("=")+1).trim()));
        data.setAd5(Integer.parseInt(arr[4].substring(arr[4].indexOf("=")+1).trim()));
        data.setAd6(Integer.parseInt(arr[5].substring(arr[5].indexOf("=")+1).trim()));
        data.setAd7(Integer.parseInt(arr[6].substring(arr[6].indexOf("=")+1).trim()));
        data.setAd8(Integer.parseInt(arr[7].substring(arr[7].indexOf("=")+1).trim()));

        if(dataList.size() > 0
                && data.getAd1().intValue() == data.getAd2().intValue()
                && data.getAd1().intValue() == data.getAd3().intValue()
                && data.getAd1().intValue() == data.getAd4().intValue()
                && data.getAd1().intValue() == data.getAd5().intValue()
                && data.getAd1().intValue() == data.getAd6().intValue()
                && data.getAd1().intValue() == data.getAd7().intValue()
                && data.getAd1().intValue() == data.getAd8().intValue() ){
            return null;
        }else {
            data.setDeal_time(new Long(System.currentTimeMillis() / 1000).intValue());

            return data;
        }
    }


    public static void main(String[] args) throws NoSuchPortException, PortInUseException, IOException {



        //SerialTool serialTool = SerialTool.getSerialTool();
        ArrayList<String> portNameList = SerialTool.findPort();
        SerialPort serialPort = null;
        for (String portName : portNameList) {
            try {
                serialPort = SerialTool.openPort(portName, 9600);

                byte[] b = new byte[]{0x00, 0x5e};
                String s = "1";
                //SerialTool.sendToPort(serialPort, s.getBytes());

                //byte[] bytes = SerialTool.readFromPort(serialPort);
                //System.out.println(new String(bytes));
                String str = SerialTool.readFromPort(serialPort);
                System.out.println(str);

                /*final SerialPort serialPort1 = serialPort;
                Thread thread = new Thread(()->{
                    byte[] bytes = SerialTool.readFromPort(serialPort1);

                    synchronized (bytes){
                        try {
                            System.out.println("进入阻塞,等待数据中...");
                            bytes.wait();
                            System.out.println("阻塞完成");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();*/

                //SerialTool.closePort(serialPort);



                /*InputStream in = null;
                byte[] bytes = null;
                try {
                    in = serialPort.getInputStream();
                    int bufflenth = in.available();        //获取buffer里的数据长度

                    String str = null;
                    String[] arr = null;
                    //bufflenth = 12;

                    while (bufflenth != 0) {
                        bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度

                        in.read(bytes);
                        bufflenth = in.available();
                        //System.out.println("bytes="+new String(bytes));
                        str = new String(bytes);
                        arr = str.split("\r\n");
                        for (String ss : arr) {
                            System.out.println("###every str="+ss);
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    //throw new ReadDataFromSerialPortFailure();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                            in = null;
                        }
                    } catch(IOException e) {
                        //throw new SerialPortInputStreamCloseFailure();
                    }

                }*/

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                SerialTool.closePort(serialPort);
            }
            System.out.println("dafd");

        }


    }


}
