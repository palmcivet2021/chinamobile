package com.chinamobile.foot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    public static String format(Date date, String pattern){
        sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);
    }
}
