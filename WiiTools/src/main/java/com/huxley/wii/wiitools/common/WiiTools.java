package com.huxley.wii.wiitools.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by huxley on 16/7/2.
 */
public class WiiTools {

    private static Context sContext;
    private static WiiTools sWiiTools;

    private WiiTools() {

    }

    public static WiiTools init(Context context) {
        WiiTools.sContext = context;
        sWiiTools = new WiiTools();
        return sWiiTools;
    }

    public WiiTools debug(){

        return sWiiTools;
    }

    public static Context getContext() {
        return sContext;
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Resources.Theme getTheme() {
        return getContext().getTheme();
    }

    public static AssetManager getAssets() {
        return getContext().getAssets();
    }

    public static Configuration getConfiguration() {
        return getResources().getConfiguration();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }
}
