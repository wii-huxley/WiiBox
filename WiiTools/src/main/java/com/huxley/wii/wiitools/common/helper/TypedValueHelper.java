package com.huxley.wii.wiitools.common.helper;

import android.util.TypedValue;

import com.huxley.wii.wiitools.common.WiiTools;

/**
 * Created by huxley on 16/7/2.
 */
public class TypedValueHelper {

    private TypedValueHelper() {
    }

    public static float applyDimension(int unit, float value) {
        return TypedValue.applyDimension(unit, value, WiiTools.getDisplayMetrics());
    }

    public static float complexToDimension(int data) {
        return TypedValue.complexToDimension(data, WiiTools.getDisplayMetrics());
    }

    public static int complexToDimensionPixelOffset(int data) {
        return TypedValue.complexToDimensionPixelOffset(data, WiiTools.getDisplayMetrics());
    }

    public static int complexToDimensionPixelSize(int data) {
        return TypedValue.complexToDimensionPixelSize(data, WiiTools.getDisplayMetrics());
    }
}
