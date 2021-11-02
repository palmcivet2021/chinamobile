package com.chinamobile.foot.util;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.StringTokenizer;

public class HexConvert {

    public static String  convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }

    public static String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for( int i=0; i<hex.length()-1; i+=2 ){

            String s = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            sb.append((char)decimal);
            sb2.append(decimal);
        }

        return sb.toString();
    }
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    //返回匹配字符
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //将字节数组转换为short类型，即统计字符串长度
    public static short bytes2Short2(byte[] b) {
        short i = (short) (((b[1] & 0xff) << 8) | b[0] & 0xff);
        return i;
    }
    //将字节数组转换为16进制字符串
    public static String BinaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }


    public static int getValueSigned(byte a, byte b) {
        int low = 0, high = 0;
        //low = b & 0x01 + b >> 1 & 0x01 * 2 + b >> 2 & 0x01 * 4 + b >> 3 & 0x01 * 8 + b >> 4 & 0x01 * 16
        int tmp = 1;
        for (int i = 0; i < 8; i++){
            low = low + (b >> i & 0x01) * tmp;
            tmp = tmp * 2;
        }
        tmp = 1;
        for (int i = 0; i < 7; i++){
            high = high + (a >> i & 0x01) * tmp;
            tmp = tmp * 2;
        }
        high = high * 256;


        if ((a >> 7 & 0x01) > 0){
            return -(32768 - high - low);

        }else{
            return high + low;
        }

    }


    public static void dealData(String str){
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

    }


    public static void main(String[] args) {


        System.out.println("======ASCII码转换为16进制======");
        String str = "*00007VERSION\\n1$";
        System.out.println("字符串: " + str);
        String hex = HexConvert.convertStringToHex(str);
        System.out.println("====转换为16进制=====" + hex);

        System.out.println("======16进制转换为ASCII======");
        System.out.println("Hex : " + hex);
        System.out.println("ASCII : " + HexConvert.convertHexToString(hex));

        byte[] bytes = HexConvert.hexStringToBytes( hex );
        System.out.println(bytes);
        System.out.println(HexConvert.BinaryToHexString( bytes ));

        byte a = (byte)0xDC;  //E9
        int a1 = a&0xFF;
        byte b = (byte)0x7C; //71
        int tt = getValueSigned(a, b);
        System.out.println("1a="+a+",1b="+b+",1tt : " + tt);

        bytes = hexStringToBytes("8E");
        System.out.println(bytes);

        java.math.BigInteger bi = new java.math.BigInteger("E9", 16);
        System.out.println("E971="+bi);

        Integer in = Integer.valueOf("10",16);
        System.out.println("in="+in);

        a = Integer.valueOf("DC",16).byteValue();
        b = Integer.valueOf("7C",16).byteValue();
        System.out.println("2a="+a+",2b="+b+",2tt : " + tt);

        String s1 = "E9";
        //System.out.println("E9="+Byte.parseByte("0xE9"));

        str = "100|66,55:200|567,90:102|43,54";

        StringTokenizer strToke = new StringTokenizer(str, ":,|");// 默认不打印分隔符
        // StringTokenizer strToke=new StringTokenizer(str,":,|",true);//打印分隔符
        // StringTokenizer strToke=new StringTokenizer(str,":,|",false);//不打印分隔符

        str = "55 B4 01 10 00 80 76 C0 C4 8D 5A 1A 9E 17 29 23 43 06 35 12 26 55 B5 01 10 00 80 26 C5 AE 9B D3 21 84 14 81 18 71 07 B7 11 34";
        str = "55 B5 01 10 00 80 26 C5 AE 9B D3 21 84 14 81 18 71 07 B7 11 34";
        str = "55 17 01 10 33 00 92 4C 00 80 16 F7 8B 50 FF 7F C9 28 00 80 E5";
        str = "55 18 01 10 C6 12 90 47 00 80 27 FB B0 50 FF 7F FB 33 00 80 FB";
        strToke = new StringTokenizer(str);
        while(strToke.hasMoreTokens()){
            System.out.println(strToke.nextToken());
        }

        dealData(str);

        long d = System.currentTimeMillis();
        int id = new Long(d / 1000).intValue();
        System.out.println(d);
        System.out.println(id);
        System.out.println(Integer.MAX_VALUE);

        Date date = new Date(1627013210197L);
        Date date1 = new Date(1627013210000L);
        System.out.println(date);
        System.out.println(date1);
    }

}
