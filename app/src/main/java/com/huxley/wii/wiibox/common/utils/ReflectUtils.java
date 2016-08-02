package com.huxley.wii.wiibox.common.utils;

import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by huxley on 16/6/16.
 */
public class ReflectUtils {

    /**
     * 通过放射获取imageView的某个属性值
     * @param object
     * @param fieldName
     * @return
     */
    public static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
