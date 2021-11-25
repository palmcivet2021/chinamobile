package com.chinamobile.digitaltwin.util;

import org.apache.commons.lang3.time.DateUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc: Sha256 加密
 * Author:
 * Date：
 */
public class Sha256Utils {
    public static void main(String[] args) {
        String str="Qwer1234";
        str = "6943c8323de32eeb7b4f6a26e3b554f4device_codeEC:B9:77:13:3E:7Eend_time2021-04-20 18:07:00start_time2021-04-20 18:05:00timestamp123456943c8323de32eeb7b4f6a26e3b554f4";
        String result=getSHA256StrJava(str);
        System.out.println("result1:"+result);

        Map map=new LinkedHashMap<String, String>();
        map.put("start_time","2021-04-20 18:05:00");
        map.put("end_time","2021-04-20 18:07:00");
        map.put("device_code","EC:B9:77:13:3E:7E");
        //map.put("timestamp","12345");
        map.put("timestamp",String.valueOf(System.currentTimeMillis()));

        Set<String> set = map.keySet();
        List<String> list1 = new ArrayList<String>(set);
        Collections.sort(list1);
        String secret = "6943c8323de32eeb7b4f6a26e3b554f4";
        StringBuilder sb = new StringBuilder(secret);
        for(int i = 0; i < list1.size(); i++){
            //System.out.println("list1(" + i + ") --> " + list1.get(i));
            sb.append(list1.get(i)).append(map.get(list1.get(i)));
        }
        sb.append(secret);

        result=getSHA256StrJava(sb.toString());
        System.out.println("result2:"+result);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        try {
            long fragmentInMinutes = DateUtils.getFragmentInMinutes(sdf.parse("2021-06-28 16:00:00"), 12);
            System.out.println("fragmentInMinutes="+fragmentInMinutes);

            Date date1 = sdf.parse("2021-06-28 16:00:00");
            Date date2 = new Date();
            System.out.println("date2="+date2);
            cal.setTime(date2);
            cal.set(Calendar.SECOND, 0);
            long minutes = (date2.getTime() - date1.getTime()) / (60 *1000);
            System.out.println("minutes="+minutes);
            minutes = (cal.getTime().getTime() - date1.getTime()) / (60 *1000);
            System.out.println("minutes="+minutes);

            for(int i=0; i<minutes/2; i++){
                date2 = DateUtils.addMinutes(date1, 2);
                System.out.println("date1="+sdf.format(date1) + ",date2=" + sdf.format(date2));
                date1 = date2;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        String line = "ad1 = 196, ad2 = 3296, ad3 = 3296, ad4 = 3296, ad5 =3296, ad6 = 3296, ad7 = 3296, da8 = 3296";
        //line = "ad1 = 196, ad2 = 3296, ad3 = 3296, ad4 = 3296, ad5 =3296, ad6 = 3296, ad7 = 3296,";
        //String pattern = "ad1\\s=\\s\\d{3,4},\\sad2\\s=\\s\\d{3,4},\\s";   \sad7\s=\s\d{3,4},
        String pattern = "ad1\\s=\\s\\d{3,4},\\sad2\\s=\\s\\d{3,4},\\sad3\\s=\\s\\d{3,4},\\sad4\\s=\\s\\d{3,4},\\sad5\\s=\\d{3,4},\\sad6\\s=\\s\\d{3,4},\\sad7\\s=\\s\\d{3,4},\\sda8\\s=\\s\\d{3,4}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        if(m.matches()){
            System.out.println("match");
        }else {
            System.out.println("not");
        }

        line = "3296,3296,3296,3296,3296,3296,3296,3296";
        line = " 652,3296,3296,3296,3296,3296,3296,3296";
        pattern = "\\s*(\\d{3,4})(\\,\\s*(\\d{3,4})){7}";
        r = Pattern.compile(pattern);
        m = r.matcher(line);
        if(m.matches()){
            System.out.println("match111");
        }else {
            System.out.println("not111");
        }


        line = "-340.042";
        pattern = "(([1-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9])";
        r = Pattern.compile(pattern);
        m = r.matcher(line);
        if(m.matches()){
            System.out.println("match222");
        }else {
            System.out.println("not222");
        }

        line = "-0.042,0.147,1.004";
        line = "-10.042,0.147,1.004";
        pattern = "\\s*((([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9]))(\\,\\s*(([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9])){2}";
        r = Pattern.compile(pattern);
        m = r.matcher(line);
        if(m.matches()){
            System.out.println("match333");
        }else {
            System.out.println("not333");
        }

        line = "3296,3296,3296,3296,3296,3296,3296,3296#-0.042,0.147,1.004#0.000,0.000,0.000#8.284,2.390,30.135# 145, 155,-388";
        line = " 652,3296,3296,3296,3296,3296,3296,3296#0.104,0.026,1.001#0.000,0.061,0.000#1.467,-6.004,27.048#  66, 201,-348";
        //line = "3296,3296,3296,3296,3296,3296,3296,3296#-1.714,10.848,842#-1.714, 10.848, 842#-1.714, 10.848, 842#-1.714, 10.848, 842";
        pattern = "\\s*(\\d{3,4})(\\,\\s*(\\d{3,4})){7}#(\\s*((([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9]))(\\,\\s*(([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9])){2})(\\#(\\s*((([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9]))(\\,\\s*(([0-9]|[-][0-9])\\d*\\.?\\d*)|(0\\.\\d*[0-9])){2})){3}";
        r = Pattern.compile(pattern);
        m = r.matcher(line);
        if(m.matches()){
            System.out.println("match444");
        }else {
            System.out.println("not444");
        }

        long d = 1630664032000L;
        System.out.println(sdf.format(new Date(d)));
        d = 1630664051000L;
        System.out.println(sdf.format(new Date(d)));

    }

    public static void getSHA256StrJava(Map<String, String> map){
        Set<String> set = map.keySet();
        List<String> list1 = new ArrayList<String>(set);
        Collections.sort(list1);
        String secret = "6943c8323de32eeb7b4f6a26e3b554f4";
        StringBuilder sb = new StringBuilder(secret);
        for(int i = 0; i < list1.size(); i++){
            sb.append(list1.get(i)).append(map.get(list1.get(i)));
        }
        sb.append(secret);

        String sign = getSHA256StrJava(sb.toString());
        map.put("sign", sign);
    }

    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr="";
        try{
            messageDigest=MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr=byte2Hex(messageDigest.digest());
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp=null;
        for(int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
