package com.huxley.wii.wiitools.common.helper;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

/**
 * Created by LeiJin01 on 2016/7/1.
 */
public class TextHelper {

    public static void aligned(TextView... textViews){
        int length = textViews.length;
        int maxLength = 0;
        int minLength = 0;
        StringBuilder insertStr;
        String[] contents = new String[length];
        /*
            获得所有的字符串
            比较得出最长的str 和 最短的str
            如果其中有字符串长度小于1,则直接retun
         */
        for (int i = 0; i < length; i++) {
            contents[i] = textViews[i].getText().toString();
            int len = contents[i].length();
            if (len > 1) {
                maxLength = Math.max(maxLength, len);
                minLength = Math.min(minLength, len);
            }
        }
        // 计算需要插入字符的 count，如果count=0，则说明所有的祖父串都一样长
        int count = (int) ((maxLength - minLength) / (Math.abs(minLength - 1)  * 1.0f));
        if (count == 0) {
            return;
        }
        insertStr = new StringBuilder();
        for (int i = 0; i < count; i++) {
            insertStr.append("正");
        }
        //根据计算的count得到的insertStr，插入长度小于maxLength的str
        SpannableString spannableString;
        for (int i = 0; i < length; i++) {
            String str = contents[i];

            int strLen = str.length();
            if (strLen < maxLength) {
                StringBuilder sb = new StringBuilder(str);
                for (int j = strLen - 1; j > 0; j--) {
                    sb.insert(j, insertStr);
                }
                float multiple = ((maxLength - strLen) * 1.0f) / ((strLen - 1) * count);
                spannableString = new SpannableString(sb);
                for (int j = 1; j < sb.length(); j = j + 1 + count) {
                    spannableString.setSpan(new RelativeSizeSpan(multiple), j, j + count, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), j, j + count,  Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                textViews[i].setText(spannableString);
            }
        }
    }

}
