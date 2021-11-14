package com.chinamobile.foot.eheaddata.obtain;

import com.chinamobile.foot.base.service.BaseDataService;
import com.chinamobile.foot.eheaddata.bean.EHeadData;
import com.chinamobile.foot.util.HexConvert;
import com.chinamobile.foot.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;


public class DealDataThread extends Thread {

    private static BaseDataService baseDataService = SpringContextUtil.getBean(BaseDataService.class);

    private BlockingQueue<String> blockingQueue;

    public DealDataThread(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run(){

        while(true){
            try {
                Thread.sleep(2000);
                //System.out.println("开始取值");
                List<String> list = new LinkedList<>();
                blockingQueue.drainTo(list);  //drainTo()将队列中的值全部从队列中移除，并赋值给对应集合
                //list.forEach(System.out::println);
                //System.out.println("############DealDataThread.run###############,list.size="+list.size());
                int count = 0;
                for (String s : list) {
                    //首先去掉尾部的 00 00 00 00 00 00 00 00 00 00
                    s = s.substring(0, s.lastIndexOf(" 00 00 00 00 00 00 00 00 00 00 "));
                    System.out.println("list.data"+(count++) + "=" + s);
                    //List<EHeadData> dataList = dealData(s);
                    //if(baseDataService == null){
                        //baseDataService = SpringContextUtil.getBean(BaseDataService.class);
                    //}
                    //baseDataService.insertBatchData(dataList);

                    //启动新线程插入mysql
                    /*new Thread(){
                        @Override
                        public void run() {
                            baseDataService.insertBatchData(dataList);
                        }
                    }.start();*/
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    private List<EHeadData> dealData(String str){
        List<EHeadData> dataList = null;
        //str=55 B4 01 10 00 80 76 C0 C4 8D 5A 1A 9E 17 29 23 43 06 35 12 26 55 B5 01 10 00 80 26 C5 AE 9B D3 21 84 14 81 18 71 07 B7 11 34
        //首先去掉尾部的 00 00 00 00 00 00 00 00 00 00
        if(str.indexOf(" 00 00 00 00 00 00 00 00 00 00 ") > 0) {
            str = str.substring(0, str.lastIndexOf(" 00 00 00 00 00 00 00 00 00 00 "));
        }
        StringTokenizer strToke = new StringTokenizer(str);
        int checkSum = 0;  //求和校验
        int count = 0;  //计数位置
        String s = null;
        int[] data = new int[8];  //8个通道数值
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
                //最后一位，检查校验和是否相等
                if(checkSum == tmp){
                    //保存data的值到mysql
                    //System.out.println("++++data="+ Arrays.toString(data));
                    EHeadData headData = convertToEhead(data);

                    if(dataList == null){
                        dataList = new ArrayList<>();
                    }
                    dataList.add(headData);
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
                    }
                }
            }

            count++;
            //System.out.println(strToke.nextToken());
        }

        return dataList;
    }


    private EHeadData convertToEhead(int[] data){
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

}
