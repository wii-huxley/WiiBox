package com.huxley.wii.wiibox.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.WiiApp;
import com.huxley.wii.wiitools.common.Utils.SPUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;


/**
 * SharedPreferences工具类
 * <p>
 * Created by huxley on 16/3/5.
 */
public class SP extends SPUtils {

    private static final String NAME_CONFIG    = "sp_config";
    private static final String NAME_GANK      = "sp_gank";
    private static final String NAME_TING      = "sp_ting";
    private static final String NAME_KNOWLEDGE = "sp_knowledge";
    private static final String NAME_USER      = "sp_user";

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
            return hasKey(get(), Constant.Key.HISTORY_LIST);
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

    public static class Knowledge {
        private static SharedPreferences sp;

        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Config.class) {
                    if (sp == null) {
                        sp = WiiApp.getContext().getSharedPreferences(NAME_KNOWLEDGE, Context.MODE_PRIVATE);
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

    public static class User {
        private static final String KEY_WEIBO_UID           = "weibo_uid";
        private static final String KEY_WEIBO_ACCESS_TOKEN  = "weibo_access_token";
        private static final String KEY_WEIBO_EXPIRES_IN    = "weibo_expires_in";
        private static final String KEY_WEIBO_REFRESH_TOKEN = "weibo_refresh_token";
        private static SharedPreferences sp;

        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (Config.class) {
                    if (sp == null) {
                        sp = WiiApp.getContext().getSharedPreferences(NAME_USER, Context.MODE_PRIVATE);
                    }
                }
            }
            return sp;
        }

        public static void saveWeiboAccessToken(Oauth2AccessToken token) {
            input(get(), KEY_WEIBO_UID, token.getUid());
            input(get(), KEY_WEIBO_ACCESS_TOKEN, token.getToken());
            input(get(), KEY_WEIBO_EXPIRES_IN, token.getExpiresTime());
            input(get(), KEY_WEIBO_REFRESH_TOKEN, token.getRefreshToken());
        }

        public static Oauth2AccessToken readWeiboAccessToken() {
            Oauth2AccessToken token = new Oauth2AccessToken();
            token.setUid((String) output(get(), KEY_WEIBO_UID, ""));
            token.setToken((String) output(get(), KEY_WEIBO_ACCESS_TOKEN, ""));
            token.setExpiresTime((Long) output(get(), KEY_WEIBO_EXPIRES_IN, 0L));
            token.setRefreshToken((String) output(get(), KEY_WEIBO_REFRESH_TOKEN, ""));
            return token;
        }
    }
}
