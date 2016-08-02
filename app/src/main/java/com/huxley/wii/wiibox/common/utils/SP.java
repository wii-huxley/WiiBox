package com.huxley.wii.wiibox.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.WiiApp;
import com.huxley.wii.wiitools.common.Utils.SPUtils;


/**
 * SharedPreferences工具类
 *
 * Created by huxley on 16/3/5.
 */
public class SP extends SPUtils {

    private static final String NAME_CONFIG = "sp_config";
    private static final String NAME_GANK = "sp_gank";
    private static final String NAME_TING = "sp_ting";

    public static class Config {
        private static SharedPreferences sp;
        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Config.class) {
                    if (sp == null) {
                        sp = WiiApp.getContext().getSharedPreferences(NAME_CONFIG, Context.MODE_PRIVATE);
                    }
                }
            }
            return sp;
        }
        public static void save(String key, Object object) {
            input(get(), key, object);
        }
        public static Object read(String key, Object defaultObject) {
            return output(get(), key, defaultObject);
        }
    }

    public static class Gank {
        private static SharedPreferences sp;
        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Gank.class) {
                    if (sp == null) {
                        sp = WiiApp.getContext().getSharedPreferences(NAME_GANK, Context.MODE_PRIVATE);
                    }
                }
            }
            return sp;
        }
        public static boolean hasHistory() {
            return hasKey(get(), Constant.Extra.HISTORY_LIST);
        }
        public static void save(String key, Object object) {
            input(get(), key, object);
        }
        public static Object read(String key, Object defaultObject) {
            return output(get(), key, defaultObject);
        }
    }

    public static class Ting56 {
        private static SharedPreferences sp;
        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Config.class) {
                    if (sp == null) {
                        sp = WiiApp.getContext().getSharedPreferences(NAME_TING, Context.MODE_PRIVATE);
                    }
                }
            }
            return sp;
        }
        public static void save(String key, Object object) {
            input(get(), key, object);
        }
        public static Object read(String key, Object defaultObject) {
            return output(get(), key, defaultObject);
        }
    }
}
