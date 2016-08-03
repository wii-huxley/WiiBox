package com.huxley.wii.wiitools.common.helper;

import android.support.annotation.ColorRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxley.wii.wiitools.R;

/**
 * Created by huxley on 16/8/2.
 */

public class SnackbarHelper {

    public static void showInfo(View rltView, String title) {
        showInfo(rltView, title, R.color.color_teal_50, R.color.color_teal_700, 0, null, null, 0, null);
    }

    public static void showInfo(View rltView, String title, @ColorRes int textColor, @ColorRes int backgroundColor, @ColorRes int actionTextColor,
                                String content, View.OnClickListener listener, int gravity, View iconView) {
        Snackbar snackbar = Snackbar.make(rltView, title, Snackbar.LENGTH_SHORT);
        if (content != null && listener != null) {
            snackbar.setAction(content, listener); // 设置 ActionText 的点击事件
            if (actionTextColor != 0) {
                snackbar.setActionTextColor(ResHelper.getColor(actionTextColor)); // ActionText 的 color
            }
        }
        View v = snackbar.getView();
        if (backgroundColor != 0) {
            v.setBackgroundColor(ResHelper.getColor(backgroundColor)); // 设置 Snackbar 的背景颜色
        }
        if (textColor != 0) {
            ((TextView) v.findViewById(R.id.snackbar_text)).setTextColor(ResHelper.getColor(textColor)); //设置字体为红色
        }
        if (gravity != 0) {
            ViewGroup.LayoutParams vLayoutParams = v.getLayoutParams();
            CoordinatorLayout.LayoutParams cLayoutParams = new CoordinatorLayout.LayoutParams(vLayoutParams.width,vLayoutParams.height);
            cLayoutParams.gravity = gravity;
            v.setLayoutParams(cLayoutParams); //设置 Snackbar 显示的位置
        }
        if (iconView != null) {
            ((Snackbar.SnackbarLayout) v).addView(iconView,0);// 设置 icon
        }
        snackbar.show();
    }
}
