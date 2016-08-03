package com.huxley.wii.wiitools.common.manager;

import android.app.Activity;

import java.util.Iterator;
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

    public static ActivityManager getInstance() {
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

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if( activityStack == null ) {
            activityStack = new Stack<>();
        }
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity(){
        if( activityStack == null ) {
            return null;
        }
        return activityStack.lastElement();
    }

    public void finishAfterTransitionActivity(Activity activity) {
        if( activityStack == null ) {
            return;
        }
        if (activity != null && !activity.isFinishing()) {
            activity.finishAfterTransition();
            activityStack.remove(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if( activityStack == null ) {
            return;
        }
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
            activityStack.remove(activity);
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        if( activityStack == null ) {
            return;
        }
        finishActivity(currentActivity());
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls){
        if( activityStack == null ) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if(activity != null && activity.getClass().equals(cls) ){
                activity.finish();
                iterator.remove();
            }
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            finishActivity(activityStack.get(i));
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Activity activity) {
        try {
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {

        }
    }
}
