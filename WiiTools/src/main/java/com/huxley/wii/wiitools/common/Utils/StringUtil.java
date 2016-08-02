package com.huxley.wii.wiitools.common.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huxley on 16/7/4.
 */
public class StringUtil {

    public static String isEmp(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str;
    }
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }


    /**
     * 输入的字符是否是汉字
     */

    public static boolean isChinese1(char a) {
        int v = (int)a;
        return (v >=19968 && v <= 171941);
    }

    /**
     * 经过测试发现此方法有一定的问题，当字符串中存在“中文符号”的时候也被认为是汉字，这是不符合我的需求的，所以有了下一个版本
     */
    public static boolean isChinese2(char a) {
        return String.valueOf(a).matches("[\u4E00-\u9FA5]"); //利用正则表达式，经测试可以区分开中文符号
    }


    public static boolean containsChinese(String s){
        if (null == s || "".equals(s.trim())) return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese2(s.charAt(i)))        return true;
        }
        return false;
    }

    public static String getCenterString (String start, String end, String content){
        Pattern pattern1 = Pattern.compile(".*?\\" + start + "(.*?)\\" + end + ".*?");
        Matcher matcher1 = pattern1.matcher(content);
        if (matcher1.matches()) {
            return matcher1.group(1);
        } else {
            return content;
        }
    }
}
