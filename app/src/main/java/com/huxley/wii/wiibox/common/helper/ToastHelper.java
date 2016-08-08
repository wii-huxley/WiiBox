package com.huxley.wii.wiibox.common.helper;


import android.support.annotation.StringRes;
import android.widget.Toast;

import com.huxley.wii.wiibox.common.WiiApp;

/**
 * 吐司帮助类
 *
 * Created by huxley on 16/3/3.
 */
public class ToastHelper {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime=0;
    private static long twoTime=0;

    public static void showInfo(Object info){
        String infoStr = String.valueOf(info);
        if(toast==null){
            toast = Toast.makeText(WiiApp.getContext(), infoStr, Toast.LENGTH_SHORT);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(info.equals(oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = infoStr;
                toast.setText(infoStr);
                toast.show();
            }
        }
        oneTime=twoTime;
    }

    public static void showInfo(@StringRes int strRecId) {
        showInfo(ResHelper.getString(strRecId));
    }
}
