package com.hvph.musicplay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HoangHVP on 10/8/2014.
 */
public final class DateTime {
    public static final String MINUTE_SECOND_FORMAT = "mm:ss";

    public static String getTimeFromMilliseconds(long millis, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(new Date(millis));
        return dateString;
    }
}
