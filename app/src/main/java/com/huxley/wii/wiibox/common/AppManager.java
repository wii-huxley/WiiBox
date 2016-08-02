package com.huxley.wii.wiibox.common;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 *
 *
 * Created by huxley on 16/2/28.
 */
public class AppManager {


    /**
     * 获取app的版本号
     * @return app版本好
     */
    public static String getVersionName() {
        //用来管理手机的APK
        PackageManager packageManager = WiiApp.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(WiiApp.getApplication().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
