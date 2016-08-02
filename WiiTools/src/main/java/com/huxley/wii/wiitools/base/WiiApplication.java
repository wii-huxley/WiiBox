package com.huxley.wii.wiitools.base;

import android.app.Application;
import android.content.Context;


/**
 * Created by huxley on 16/7/10.
 */
public class WiiApplication extends Application {

    private static Context mContext;
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static Context getContext() {
        return mContext;
    }
}
