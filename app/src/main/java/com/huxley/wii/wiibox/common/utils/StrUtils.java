package com.huxley.wii.wiibox.common.utils;

/**
 * Created by huxley on 16/3/6.
 */
public class StrUtils {

    public static boolean isEmp(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }
}
