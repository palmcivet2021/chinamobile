package com.chinamobile.foot.ehead;

import com.chinamobile.config.PropertiesListenerConfig;
import com.chinamobile.foot.bean.EHeadData;
import com.chinamobile.foot.service.BaseDataService;
import com.chinamobile.foot.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class DealEHeadData {
    private static BaseDataService baseDataService = SpringContextUtil.getBean(BaseDataService.class);
    private static BlockingQueue<long[]> blockingQueue = ThreadPoolUtil.getBlockingQueue();
    private static List<EHeadData> longList = new ArrayList<>();
    //private static String path = "D:\\project\\文档\\移动\\mat\\";
    //private static String path = "C:\\braindcode\\arl-eegmodels\\examples";
    private static String path = PropertiesListenerConfig.getProperty("mat.local.path");
    private static long start = 0;
    //private static List<long[]> longList = new ArrayList<>();

    public static List<EHeadData> dealData(String str){
        final List<EHeadData> dataList = new ArrayList<>();
        final List<long[]> arrList = new ArrayList<>();
        //str=55 B4 01 10 00 80 76 C0 C4 8D 5A 1A 9E 17 29 23 43 06 35 12 26 55 B5 01 10 00 80 26 C5 AE 9B D3 21 84 14 81 18 71 07 B7 11 34
        //首先去掉尾部的 00 00 00 00 00 00 00 00 00 00
        if(str.indexOf(" 00 00 00 00 00 00 00 00 00 00 ") > 0) {
            str = str.substring(0, str.lastIndexOf(" 00 00 00 00 00 00 00 00 00 00 "));
        }
        System.out.println("DealEHeadData.dealData" + "=" + str);
        StringTokenizer strToke = new StringTokenizer(str);
        int checkSum = 0;  //求和校验
        int count = 0;  //计数位置
        int num = 0;    //个数
        String s = null;
        int[] data = new int[8];  //8个通道数值
        long[] dataLong = new long[8]; //8个通道数值
        byte low = 0, high = 0;
        byte tmp = 0;
        boolean isEnd = false;

        while(strToke.hasMoreTokens()){
            s = strToke.nextToken();
            tmp = Integer.valueOf(s,16).byteValue();
            if(s.equals("55")){
                //每个完整的数据从55开始
                checkSum = 0;
                count = 0;
                isEnd = false;
            }

            if(count > 19){
                num++;
                //最后一位，检查校验和是否相等
                if(checkSum == tmp){
                    //保存data的值到mysql
                    //System.out.println("++++data="+ Arrays.toString(data));
                    EHeadData headData = convertToEhead(data);

                    dataList.add(headData);
                    arrList.add(dataLong);
                    //blockingQueue.offer(dataLong);
                    longList.add(headData);
                    //longList.add(convertToEhead(dataLong));
                }
            }else{
                checkSum += tmp;

                if(count >= 4){
                    if(count % 2 == 0) {
                        low = tmp;
                    }else {
                        high = tmp;
                        int tt = HexConvert.getValueSigned(high, low);
                        data[(count - 5)/2] = tt;
                        dataLong[(count - 5)/2] = tt;
                    }
                }
            }

            count++;
            //System.out.println(strToke.nextToken());
        }
        System.out.println("############num="+num);
        /*if(!CollectionUtils.isEmpty(dataList)){
            ExecutorService threadPool = ThreadPoolUtil.getIntance();
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("#####blockingQueue.size="+blockingQueue.size());
                    //baseDataService.insertBatchData(dataList);
                    //int[][] a = (int[][]) arrList.toArray();
                    long[][] a = new long[arrList.size()][];
                    for (int i=0; i<arrList.size(); i++) {
                        a[i] = arrList.get(i);
                    }
                    String fileName = new Long(System.currentTimeMillis()/1000).toString() + ".mat";
                    String path = "D:\\project\\文档\\移动\\mat\\";
                    try {
                        MatUtil.writeMatFile(path+fileName, a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/

        //
        /*if(blockingQueue.size() >= ThreadPoolUtil.BLOCKINGQUEUE_SIZE){
            ExecutorService threadPool = ThreadPoolUtil.getIntance();
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    List<long[]> arrList = new LinkedList<>();
                    blockingQueue.drainTo(arrList);  //drainTo()将队列中的值全部从队列中移除，并赋值给对应集合

                    long[][] a = new long[arrList.size()][];
                    for (int i=0; i<arrList.size(); i++) {
                        a[i] = arrList.get(i);
                    }
                    String fileName = new Long(System.currentTimeMillis()/1000).toString() + ".mat";
                    String path = "D:\\project\\文档\\移动\\mat\\";
                    try {
                        MatUtil.writeMatFile(path+fileName, a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/

        //long start = 0;
        //if(longList.size() == 0){
            //start = System.currentTimeMillis();
        //}
        long endTime = System.currentTimeMillis();
        System.out.println("############start="+EheadServer.startTime+",end="+endTime+",耗时："+(endTime-EheadServer.startTime)/1000 + "秒");
        //if(longList.size() >= ThreadPoolUtil.BLOCKINGQUEUE_SIZE){  //1==2 &&
        //每隔6秒生成一个文件
        if((endTime - EheadServer.startTime) / 1000 > 6){

            ExecutorService threadPool = ThreadPoolUtil.getIntance();
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    List<EHeadData> arrList = new LinkedList<>();
                    arrList.addAll(longList); //drainTo()将队列中的值全部从队列中移除，并赋值给对应集合
                    longList.clear();
                    EheadServer.startTime = System.currentTimeMillis();

                    long[][] a = new long[arrList.size()][8];
                    for (int i=0; i<arrList.size(); i++) {
                        //a[i] = arrList.get(i);
                        a[i][0] = arrList.get(i).getChannel_1().longValue();
                        a[i][1] = arrList.get(i).getChannel_2().longValue();
                        a[i][2] = arrList.get(i).getChannel_3().longValue();
                        a[i][3] = arrList.get(i).getChannel_4().longValue();
                        a[i][4] = arrList.get(i).getChannel_5().longValue();
                        a[i][5] = arrList.get(i).getChannel_6().longValue();
                        a[i][6] = arrList.get(i).getChannel_7().longValue();
                        a[i][7] = arrList.get(i).getChannel_8().longValue();
                    }
                    /*String fileName = path + new Long(System.currentTimeMillis()/1000).toString() + ".mat";
                    String path = "D:\\project\\文档\\移动\\mat\\";
                    try {
                        MatUtil.writeMatFile(fileName, a);
                        RemoteUploadFileUtil.uploadToRemote("", fileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    threadPool.submit(new Runnable() {
                        @Override
                        public void run() {
                            String fileName = new Long(System.currentTimeMillis()/1000).toString() + ".mat";
                            //String path = "D:\\project\\文档\\移动\\mat\\";
                            try {
                                System.out.println("############path="+path);
                                MatUtil.writeMatFile(path + fileName, a);
                                RemoteUploadFileUtil.uploadToRemote(path, fileName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });
        }


        /*if(longList.size() >= ThreadPoolUtil.BLOCKINGQUEUE_SIZE){
            ExecutorService threadPool = ThreadPoolUtil.getIntance();
            threadPool.submit(new Runnable() {
                @Override
                public void run() {

                    long[][] a = new long[longList.size()][];
                    for (int i=0; i<arrList.size(); i++) {
                        a[i] = longList.get(i);
                    }

                    //long[][] a = (long[][])longList.toArray();


                    String fileName = new Long(System.currentTimeMillis()/1000).toString() + ".mat";
                    String path = "D:\\project\\文档\\移动\\mat\\";
                    try {
                        MatUtil.writeMatFile(path+fileName, a);
                        longList.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/

        return dataList;
    }


    public static void saveData(){
        if(longList.size() > 0) {
            ExecutorService threadPool = ThreadPoolUtil.getIntance();
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    List<EHeadData> arrList = new LinkedList<>();
                    arrList.addAll(longList); //drainTo()将队列中的值全部从队列中移除，并赋值给对应集合
                    longList.clear();
                    start = System.currentTimeMillis();

                    long[][] a = new long[arrList.size()][8];
                    for (int i = 0; i < arrList.size(); i++) {
                        //a[i] = arrList.get(i);
                        a[i][0] = arrList.get(i).getChannel_1().longValue();
                        a[i][1] = arrList.get(i).getChannel_2().longValue();
                        a[i][2] = arrList.get(i).getChannel_3().longValue();
                        a[i][3] = arrList.get(i).getChannel_4().longValue();
                        a[i][4] = arrList.get(i).getChannel_5().longValue();
                        a[i][5] = arrList.get(i).getChannel_6().longValue();
                        a[i][6] = arrList.get(i).getChannel_7().longValue();
                        a[i][7] = arrList.get(i).getChannel_8().longValue();
                    }

                    threadPool.submit(new Runnable() {
                        @Override
                        public void run() {
                            String fileName = new Long(System.currentTimeMillis() / 1000).toString() + ".mat";
                            //String fileName = "("+(++EheadServer.fileNum)+")"+ new Long(System.currentTimeMillis() / 1000).toString() + ".mat";
                            //String path = "D:\\project\\文档\\移动\\mat\\";
                            try {
                                if(a.length > 0) {
                                    MatUtil.writeMatFile(path + fileName, a);
                                    RemoteUploadFileUtil.uploadToRemote(path, fileName);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });
        }

    }


    public static EHeadData convertToEhead(int[] data){
        EHeadData headData = new EHeadData();
        headData.setChannel_1(data[0]);
        headData.setChannel_2(data[1]);
        headData.setChannel_3(data[2]);
        headData.setChannel_4(data[3]);
        headData.setChannel_5(data[4]);
        headData.setChannel_6(data[5]);
        headData.setChannel_7(data[6]);
        headData.setChannel_8(data[7]);

        headData.setDeal_time(new Long(System.currentTimeMillis()/1000).intValue());

        return headData;
    }


    public static long[] convertToEhead(long[] data){
        long[] headData = data.clone();
        return headData;
    }

}
