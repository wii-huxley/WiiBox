package com.huxley.wii.wiibox.common.utils;

import android.util.Log;

import com.huxley.wii.wiibox.common.Constant;

/**
 * 日志工具类
 *
 * Created by huxley on 16/3/26.
 */
public class WiiLog {

    private static boolean isOpenLog = true;

    public static void i(Object... objects) {
        if (!isOpenLog) {
            return;
        }
        StringBuilder content = new StringBuilder();
        for (Object object : objects) {
            content.append(object);
        }
        Log.i(Constant.LOG_NAME, content.toString());
    }

    public static void e(Object... objects) {
        StringBuilder content = new StringBuilder();
        for (Object object : objects) {
            content.append(object);
        }
        Log.e(Constant.LOG_NAME, content.toString());
    }

//    public static void d(String info) {
//        if (!BuildConfig.DEBUG) {
//            return;
//        }
//        Log.d(Constant.LOG_NAME, info);
//    }




    /** 详细信息 verboseLog */
    public static void v(String msg) {
        if (Constant.Debug.TEST_LOG) {
            Log.v(Constant.Debug.TAG_LOG, msg);
        }
    }
    public static void v(String msg, Throwable throwable) {
        if (Constant.Debug.TEST_LOG) {
            Log.v(Constant.Debug.TAG_LOG, msg, throwable);
        }
    }
    public static void v(String msg, Object... args) {
        if (Constant.Debug.TEST_LOG) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.v(Constant.Debug.TAG_LOG, msg);
        }
    }

    /** 调试信息 debugLog */
    public static void d(String msg) {
        if (Constant.Debug.TEST_LOG) {
            Log.d(Constant.Debug.TAG_LOG, msg);
        }
    }
    public static void d(String msg, Throwable throwable) {
        if (Constant.Debug.TEST_LOG) {
            Log.d(Constant.Debug.TAG_LOG, msg, throwable);
        }
    }
    public static void d(String msg, Object... args) {
        if (Constant.Debug.TEST_LOG) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.d(Constant.Debug.TAG_LOG, msg);
        }
    }

    /** 一般信息 infoLog */
    public static void i(String msg) {
        if (Constant.Debug.TEST_LOG) {
            Log.i(Constant.Debug.TAG_LOG, msg);
        }
    }
    public static void i(String msg, Throwable throwable) {
        if (Constant.Debug.TEST_LOG) {
            Log.i(Constant.Debug.TAG_LOG, msg, throwable);
        }
    }
    public static void i(String msg, Object... args) {
        if (Constant.Debug.TEST_LOG) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.i(Constant.Debug.TAG_LOG, msg);
        }
    }

    /** 警告信息 warnLog */
    public static void w(String msg) {
        if (Constant.Debug.TEST_LOG) {
            Log.w(Constant.Debug.TAG_LOG, msg);
        }
    }
    public static void w(String msg, Throwable throwable) {
        if (Constant.Debug.TEST_LOG) {
            Log.w(Constant.Debug.TAG_LOG, msg, throwable);
        }
    }
    public static void w(String msg, Object... args) {
        if (Constant.Debug.TEST_LOG) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.w(Constant.Debug.TAG_LOG, msg);
        }
    }

    /** 错误信息 errorLog */
    public static void e(String msg) {
        if (Constant.Debug.TEST_LOG) {
            Log.e(Constant.Debug.TAG_LOG, msg);
        }
    }
    public static void e(String msg, Throwable throwable) {
        if (Constant.Debug.TEST_LOG) {
            Log.e(Constant.Debug.TAG_LOG, msg, throwable);
        }
    }
    public static void e(String msg, Object... args) {
        if (Constant.Debug.TEST_LOG) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.e(Constant.Debug.TAG_LOG, msg);
        }
    }

    /** 断言信息 assertLog */
    public static void f(String msg) {
        if (Constant.Debug.TEST_LOG) {
            Log.wtf(Constant.Debug.TAG_LOG, msg);
        }
    }
    public static void f(String msg, Throwable throwable) {
        if (Constant.Debug.TEST_LOG) {
            Log.wtf(Constant.Debug.TAG_LOG, msg, throwable);
        }
    }
    public static void f(String msg, Object... args) {
        if (Constant.Debug.TEST_LOG) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.wtf(Constant.Debug.TAG_LOG, msg);
        }
    }

    public static void tall(String msg) {
        Log.i(Constant.Debug.TAG_LOG, msg);
    }
}
