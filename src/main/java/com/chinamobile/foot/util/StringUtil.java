package com.chinamobile.foot.util;



import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 字符串工具
 *
 * <p>字符串工具</p>
 * @author
 * @version V1.0
 */
public class StringUtil {

    /**
     * 转换字符串数组到int数组
     *
     * <p>转换字符串到int数组</p>
     * @param str
     * @param delimiter
     * @return int[]
     * @throws
     */
    public static int[] getIntArrayFromString(String str, String delimiter){
        if(StringUtils.isEmpty(str)){
            return new int[0];
        }

        int[] result = getIntArrayFromStringArray(str.split(delimiter));

        return result;
    }


    /**
     * 转换字符串数组到float数组
     *
     * <p>转换字符串到int数组</p>
     * @param str
     * @param delimiter
     * @return float[]
     * @throws
     */
    public static float[] getFloatArrayFromString(String str, String delimiter){
        if(StringUtils.isEmpty(str)){
            return new float[0];
        }

        float[] result = getFloatArrayFromStringArray(str.split(delimiter));

        return result;
    }

    /**
     * 转换字符串数组到int数组
     *
     * <p>转换字符串数组到int数组</p>
     * @param strArray
     * @return int[]
     * @throws
     */
    public static int[] getIntArrayFromStringArray(String[] strArray){
        if(strArray == null){
            return new int[0];
        }
        int[] result = new int[strArray.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.valueOf(strArray[i]);
        }
        return result;
    }


    /**
     * 转换字符串数组到float数组
     *
     * <p>转换字符串数组到int数组</p>
     * @param strArray
     * @return float[]
     * @throws
     */
    public static float[] getFloatArrayFromStringArray(String[] strArray){
        if(strArray == null){
            return new float[0];
        }
        float[] result = new float[strArray.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Float.valueOf(strArray[i]);
        }
        return result;
    }


    public static void main(String[] args) {
        String table_id="1,2,3,11,20,100";

        //System.out.println(Arrays.toString(getIntArrayFromStringArray(table_id.split(","))));
        System.out.println(Arrays.toString(getIntArrayFromString(table_id, ",")));

        int[] a = StringUtils.toCodePoints(",");
        System.out.println();

    }


}
