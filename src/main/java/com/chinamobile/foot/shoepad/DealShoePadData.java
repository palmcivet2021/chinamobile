package com.chinamobile.foot.shoepad;

import com.chinamobile.foot.bean.ShoePadData;
import com.chinamobile.foot.service.BaseDataService;
import com.chinamobile.foot.util.SpringContextUtil;
import com.chinamobile.foot.util.ThreadPoolUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DealShoePadData {
    private static BaseDataService baseDataService = SpringContextUtil.getBean(BaseDataService.class);
    private static List<ShoePadData> dataList = new ArrayList<>();
    private static Pattern r = null;
    private static Matcher m = null;

    static {
        String pattern = "\\s*(\\d{3,4})(\\,\\s*(\\d{3,4})){7}#(\\s*((([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9]))(\\,\\s*(([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9])){2})(\\#(\\s*((([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9]))(\\,\\s*(([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9])){2})){3}";
        r = Pattern.compile(pattern);
    }

    public static List<ShoePadData> dealData(String str){
        m = r.matcher(str);
        if(m.matches()){
            ShoePadData data = converToShoePadData(str);
            if(data != null) {
                dataList.add(data);
            }
        }


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


        return null;
    }



    public static ShoePadData converToShoePadData(String str){
        ShoePadData data = new ShoePadData();
        /*String[] arr = str.split(",");
        data.setAd1(Integer.parseInt(arr[0].substring(arr[0].indexOf("=")+1).trim()));
        data.setAd2(Integer.parseInt(arr[1].substring(arr[1].indexOf("=")+1).trim()));
        data.setAd3(Integer.parseInt(arr[2].substring(arr[2].indexOf("=")+1).trim()));
        data.setAd4(Integer.parseInt(arr[3].substring(arr[3].indexOf("=")+1).trim()));
        data.setAd5(Integer.parseInt(arr[4].substring(arr[4].indexOf("=")+1).trim()));
        data.setAd6(Integer.parseInt(arr[5].substring(arr[5].indexOf("=")+1).trim()));
        data.setAd7(Integer.parseInt(arr[6].substring(arr[6].indexOf("=")+1).trim()));
        data.setAd8(Integer.parseInt(arr[7].substring(arr[7].indexOf("=")+1).trim()));*/

        String[] arr = str.split("#");
        String[] arr1 = arr[0].split(",");    //ad1~ad8压力传感器读数
        data.setAd1(Integer.parseInt(arr1[0].trim()));
        data.setAd2(Integer.parseInt(arr1[1].trim()));
        data.setAd3(Integer.parseInt(arr1[2].trim()));
        data.setAd4(Integer.parseInt(arr1[3].trim()));
        data.setAd5(Integer.parseInt(arr1[4].trim()));
        data.setAd6(Integer.parseInt(arr1[5].trim()));
        data.setAd7(Integer.parseInt(arr1[6].trim()));
        data.setAd8(Integer.parseInt(arr1[7].trim()));

        arr1 = arr[1].split(",");    //加速度，共x,y,x三组数据
        data.setAcceleration_x(Float.parseFloat(arr1[0].trim()));
        data.setAcceleration_y(Float.parseFloat(arr1[1].trim()));
        data.setAcceleration_z(Float.parseFloat(arr1[2].trim()));

        arr1 = arr[2].split(",");    //角速度，共x,y,x三组数据
        data.setAngular_speed_x(Float.parseFloat(arr1[0].trim()));
        data.setAngular_speed_y(Float.parseFloat(arr1[1].trim()));
        data.setAngular_speed_z(Float.parseFloat(arr1[2].trim()));

        arr1 = arr[3].split(",");    //角度，共x,y,x三组数据
        data.setAngle_x(Float.parseFloat(arr1[0].trim()));
        data.setAngle_y(Float.parseFloat(arr1[1].trim()));
        data.setAngle_z(Float.parseFloat(arr1[2].trim()));

        arr1 = arr[4].split(",");    //磁场强度，共x,y,x三组数据
        data.setMagnetic_strength_x(Float.parseFloat(arr1[0].trim()));
        data.setMagnetic_strength_y(Float.parseFloat(arr1[1].trim()));
        data.setMagnetic_strength_z(Float.parseFloat(arr1[2].trim()));

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


}
