package com.huxley.wii.wiibox.common.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * Created by huxley on 16/2/28.
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;

    private static ActivityManager activityManager;

    private ActivityManager() {
        activityStack = new Stack<>();
    }

    public static ActivityManager newInstance() {
        if (activityManager == null) {
            synchronized (ActivityManager.class) {
                if (activityManager == null) {
                    if (activityManager == null) {
                        activityManager = new ActivityManager();
                    }
                }
            }
        }
        return activityManager;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
            activityStack.remove(activity);
        }
    }

    public void removeAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            removeActivity(activityStack.get(i));
        }
        activityStack.clear();
    }
}
