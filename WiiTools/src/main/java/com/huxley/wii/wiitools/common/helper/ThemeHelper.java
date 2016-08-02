package com.huxley.wii.wiitools.common.helper;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.huxley.wii.wiitools.common.WiiTools;

/**
 * 主题帮助类
 * Created by huxley on 16/7/2.
 */
public class ThemeHelper {

    private ThemeHelper() {
    }

    public static void applyStyle(int resId, boolean force) {
        WiiTools.getTheme().applyStyle(resId, force);
    }

    public static void dump(int priority, String tag, String prefix) {
        WiiTools.getTheme().dump(priority, tag, prefix);
    }

    @TargetApi(23)
    public static int getChangingConfigurations() {
        return WiiTools.getTheme().getChangingConfigurations();
    }

    public static TypedArray obtainStyledAttributes(@StyleableRes int[] attrs) {
        return WiiTools.getTheme().obtainStyledAttributes(attrs);
    }

    public static TypedArray obtainStyledAttributes(@StyleRes int resid, @StyleableRes int[] attrs) {
        return WiiTools.getTheme().obtainStyledAttributes(resid, attrs);
    }

    public static TypedArray obtainStyledAttributes(AttributeSet set, @StyleableRes int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        return WiiTools.getTheme().obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
    }

    public static boolean resolveAttribute(int resid, TypedValue outValue, boolean resolveRefs) {
        return WiiTools.getTheme().resolveAttribute(resid, outValue, resolveRefs);
    }

    public static void setTo(Resources.Theme other) {
        WiiTools.getTheme().setTo(other);
    }
}
