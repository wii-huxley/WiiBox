package com.huxley.wii.wiitools.common.Utils;

/**
 * Created by huxley on 16/7/31.
 */

public class DateUtils {


    public static String formatCodekkDate(String date) {
        if (date == null || date.length() < 10) {
            return "";
        }
        return date.substring(0, 10);
    }
}
