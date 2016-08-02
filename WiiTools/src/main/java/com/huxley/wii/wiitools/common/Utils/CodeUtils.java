package com.huxley.wii.wiitools.common.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * encode : 加密
 * decode : 解密
 * Created by huxley on 16/7/12.
 */
public class CodeUtils {

    private static final char LOWER_HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char CAPITAL_HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final int[] base64DecodeChars = new int[]{
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
            -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
    };


    public static String urlDecode(String content, String charsetName) {
        try {
            return URLDecoder.decode(content, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String urlEncode(String content, String charsetName) {
        try {
            return URLEncoder.encode(content, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * md5加密
     *
     * @param content   需要加密的文本
     * @param isCapital true：加密成大写
     *                  false：加密成小写
     * @return 加密后的文本
     */
    public static String md5Encoder(String content, boolean isCapital) {
        try {
            byte[] strTemp = content.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] mds = mdTemp.digest();
            int j = mds.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte md : mds) {
                if (isCapital) {
                    str[k++] = CAPITAL_HEX_DIGITS[md >> 4 & 0xf];
                    str[k++] = CAPITAL_HEX_DIGITS[md & 0xf];
                } else {
                    str[k++] = LOWER_HEX_DIGITS[md >> 4 & 0xf];
                    str[k++] = LOWER_HEX_DIGITS[md & 0xf];
                }
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }


    public static String utf16to8(String str) {
        int i = 0, len = str.length(), c;
        String out = "";
        while (i < len) {
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += (char) (0xE0 | ((c >> 12) & 0x0F));
                out += (char) (0x80 | ((c >> 6) & 0x3F));
                out += (char) (0x80 | (c & 0x3F));
            } else {
                out += (char) (0xC0 | ((c >> 6) & 0x1F));
                out += (char) (0x80 | (c & 0x3F));
            }
            i++;
        }
        return out;
    }


    public static String utf8to16(String str) {
        int i = 0, len = str.length(), c;
        char char2, char3;
        String out = "";
        while (i < len) {
            c = str.charAt(i++);
            switch (c >> 4) {
                // 0xxxxxxx
                case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:
                    out += str.charAt(i - 1);
                    break;
                // 110x xxxx 10xx xxxx
                case 12:case 13:
                    char2 = str.charAt(i++);
                    out += (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                // 1110 xxxx 10xx xxxx 10xx xxxx
                case 14:
                    char2 = str.charAt(i++);
                    char3 = str.charAt(i++);
                    out += (char) (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | (char3 & 0x3F));
                    break;
            }
        }
        return out;
    }

    public static String base64encode(String str) {
        int i = 0, len = str.length(), c1, c2, c3;
        String out = "";
        while (i < len) {
            c1 = str.charAt(i++) & 0xff;
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt((c1 & 0x3) << 4);
                out += "==";
                break;
            }
            c2 = str.charAt(i++);
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                out += base64EncodeChars.charAt((c2 & 0xF) << 2);
                out += "=";
                break;
            }
            c3 = str.charAt(i++);
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
            out += base64EncodeChars.charAt(c3 & 0x3F);
        }
        return out;
    }


    public static String base64decode(String str) {
        char c1, c2, c3, c4;
        int len = str.length();
        int i = 0;
        String out = "";

        while (i < len) {

            do {
                c1 = (char) base64DecodeChars[str.charAt(i++) & 0xff];
            } while (i < len && c1 == -1);

            if (c1 == -1)
                break;

            do {
                c2 = (char) base64DecodeChars[str.charAt(i++) & 0xff];
            } while (i < len && c2 == -1);

            if (c2 == -1)
                break;

            out += (char) ((c1 << 2) | ((c2 & 0x30) >> 4));

            do {
                c3 = (char) (str.charAt(i++) & 0xff);
                if (c3 == 61)
                    return out;
                c3 = (char) base64DecodeChars[c3];
            } while (i < len && c3 == -1);

            if (c3 == -1)
                break;

            out += (char) (((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

            do {
                c4 = (char) (str.charAt(i++) & 0xff);
                if (c4 == 61)
                    return out;
                c4 = (char) base64DecodeChars[c4];
            } while (i < len && c4 == -1);

            if (c4 == -1)
                break;

            out += (char) (((c3 & 0x03) << 6) | c4);
        }
        return out;
    }
}
