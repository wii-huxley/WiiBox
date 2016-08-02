package com.huxley.wii.wiitools.common.helper;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnyRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.huxley.wii.wiitools.common.WiiTools;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源帮助
 * Created by huxley on 16/7/2.
 */
public class ResourcesHelper {

    // No Instance
    protected ResourcesHelper() {
    }

    private static void finishPreloading() {
        WiiTools.getResources().finishPreloading();
    }

    private static void flushLayoutCache() {
        WiiTools.getResources().flushLayoutCache();
    }

    public static XmlResourceParser getAnimation(@AnimRes int animRes) {
        return WiiTools.getResources().getAnimation(animRes);
    }

    public static AssetManager getAssets() {
        return WiiTools.getResources().getAssets();
    }

    public static boolean getBoolean(@BoolRes int boolRes) {
        return WiiTools.getResources().getBoolean(boolRes);
    }

    @ColorInt
    public static int getColor(@ColorRes int colorRes) {
        return ContextHelper.getColor(colorRes);
    }

    @ColorInt
    public static int getColor(@ColorRes int colorRes, Resources.Theme theme) {
        if (VersionHelper.require(23))
            return WiiTools.getResources().getColor(colorRes, theme);
        else
            return getColor(colorRes);
    }

    public static ColorStateList getColorStateList(@ColorRes int colorRes) {
        return ContextHelper.getColorStateList(colorRes);
    }

    public static ColorStateList getColorStateList(@ColorRes int colorRes, Resources.Theme theme) {
        if (VersionHelper.require(23))
            return WiiTools.getResources().getColorStateList(colorRes, theme);
        else
            return getColorStateList(colorRes);
    }

    public static Configuration getConfiguration() {
        return WiiTools.getConfiguration();
    }

    public static float getDimension(@DimenRes int dimenRes) {
        return WiiTools.getResources().getDimension(dimenRes);
    }

    public static int getDimensionPixelOffset(@DimenRes int dimenRes) {
        return WiiTools.getResources().getDimensionPixelOffset(dimenRes);
    }

    public static int getDimensionPixelSize(@DimenRes int dimenRes) {
        return WiiTools.getResources().getDimensionPixelSize(dimenRes);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return WiiTools.getDisplayMetrics();
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        return ContextHelper.getDrawable(drawableRes);
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes, Resources.Theme theme) {
        if (VersionHelper.require(21))
            return WiiTools.getResources().getDrawable(drawableRes, theme);
        else
            return WiiTools.getResources().getDrawable(drawableRes);
    }

    public static Drawable getDrawableForDensity(@DrawableRes int drawableRes, int density) {
        if (VersionHelper.require(21))
            return WiiTools.getResources().getDrawableForDensity(drawableRes, density, WiiTools.getContext().getTheme());
        else if (VersionHelper.require(15))
            return WiiTools.getResources().getDrawableForDensity(drawableRes, density);
        else
            return WiiTools.getResources().getDrawable(drawableRes);
    }

    public static float getFraction(int id, int base, int pbase) {
        return WiiTools.getResources().getFraction(id, base, pbase);
    }

    public static int getIdentifier(String name, String defType, String defPackage) {
        return WiiTools.getResources().getIdentifier(name, defType, defPackage);
    }

    public static int[] getIntArray(@ArrayRes int arrayRes) {
        return WiiTools.getResources().getIntArray(arrayRes);
    }

    public static int getInteger(@IntegerRes int integerRes) {
        return WiiTools.getResources().getInteger(integerRes);
    }

    public static XmlResourceParser getLayout(@LayoutRes int layoutRes) {
        return WiiTools.getResources().getLayout(layoutRes);
    }

    public static Movie getMovie(@RawRes int rawRes) {
        return WiiTools.getResources().getMovie(rawRes);
    }

    public static String getQuantityString(int id, int quantity, Object... formatArgs) {
        return WiiTools.getResources().getQuantityString(id, quantity, formatArgs);
    }

    public static String getQuantityString(@PluralsRes int pluralsRes, int quantity) throws Resources.NotFoundException {
        return WiiTools.getResources().getQuantityString(pluralsRes, quantity);
    }

    public static CharSequence getQuantityText(int id, int quantity) {
        return WiiTools.getResources().getQuantityText(id, quantity);
    }

    public static String getResourceEntryName(@AnyRes int anyRes) {
        return WiiTools.getResources().getResourceEntryName(anyRes);
    }

    public static String getResourceName(@AnyRes int anyRes) {
        return WiiTools.getResources().getResourceName(anyRes);
    }

    public static String getResourcePackageName(@AnyRes int anyRes) {
        return WiiTools.getResources().getResourcePackageName(anyRes);
    }

    public static String getResourceTypeName(@AnyRes int anyRes) {
        return WiiTools.getResources().getResourceTypeName(anyRes);
    }

    public static String getString(@StringRes int stringRes) {
        return WiiTools.getResources().getString(stringRes);
    }

    public static String getString(@StringRes int stringRes, Object... formatArgs) {
        return WiiTools.getResources().getString(stringRes, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int arrayRes) {
        return WiiTools.getResources().getStringArray(arrayRes);
    }

    public static Resources getSystem() {
        return WiiTools.getResources().getSystem();
    }

    public static CharSequence getText(@StringRes int stringRes, CharSequence def) {
        return WiiTools.getResources().getText(stringRes, def);
    }

    public static CharSequence getText(@StringRes int stringRes) {
        return WiiTools.getResources().getText(stringRes);
    }

    public static CharSequence[] getTextArray(@ArrayRes int arrayRes) {
        return WiiTools.getResources().getTextArray(arrayRes);
    }

    public static void getValue(String name, TypedValue outValue, boolean resolveRefs) {
        WiiTools.getResources().getValue(name, outValue, resolveRefs);
    }

    public static void getValue(@AnyRes int anyRes, TypedValue outValue, boolean resolveRefs) {
        WiiTools.getResources().getValue(anyRes, outValue, resolveRefs);
    }

    public static void getValueForDensity(@AnyRes int anyRes, int density, TypedValue outValue, boolean resolveRefs) {
        if (VersionHelper.require(15))
            WiiTools.getResources().getValueForDensity(anyRes, density, outValue, resolveRefs);
        else
            WiiTools.getResources().getValue(anyRes, outValue, resolveRefs);
    }

    public static XmlResourceParser getXml(@XmlRes int xmlRes) {
        return WiiTools.getResources().getXml(xmlRes);
    }

    public static Resources.Theme newTheme() {
        return WiiTools.getResources().newTheme();
    }

    public static TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
        return WiiTools.getResources().obtainAttributes(set, attrs);
    }

    public static TypedArray obtainTypedArray(@ArrayRes int anyRes) {
        return WiiTools.getResources().obtainTypedArray(anyRes);
    }

    public static InputStream openRawResource(@RawRes int rawRes) {
        return WiiTools.getResources().openRawResource(rawRes);
    }

    public static InputStream openRawResource(@RawRes int rawRes, TypedValue value) {
        return WiiTools.getResources().openRawResource(rawRes, value);
    }

    public static AssetFileDescriptor openRawResourceFd(@RawRes int rawRes) {
        return WiiTools.getResources().openRawResourceFd(rawRes);
    }

    public static void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle) throws XmlPullParserException {
        WiiTools.getResources().parseBundleExtra(tagName, attrs, outBundle);
    }

    public static void parseBundleExtras(XmlResourceParser parser, Bundle outBundle) throws XmlPullParserException, IOException {
        WiiTools.getResources().parseBundleExtras(parser, outBundle);
    }

    public static void updateConfiguration(Configuration config, DisplayMetrics metrics) {
        WiiTools.getResources().updateConfiguration(config, metrics);
    }

    // Added methods
    public static int[] getColorArray(@ArrayRes int array) {
        if (array == 0)
            return null;

        TypedArray typedArray = WiiTools.getResources().obtainTypedArray(array);
        int[] colors = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++)
            colors[i] = typedArray.getColor(i, 0);
        typedArray.recycle();
        return colors;
    }
}
