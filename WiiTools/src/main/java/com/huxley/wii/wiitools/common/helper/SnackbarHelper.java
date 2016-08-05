package com.huxley.wii.wiitools.common.helper;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
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

    private static final int COLOR_CONTENT_TEXT = R.color.color_teal_50;
    private static final int COLOR_ACTION_TEXT = R.color.color_amber_500;
    private static final int COLOR_BACK_GROUND = R.color.color_teal_700;


    public static void showInfo(View rltView, String title) {
        showInfo(rltView, title, Snackbar.LENGTH_SHORT, COLOR_CONTENT_TEXT, COLOR_BACK_GROUND, 0, null, null, 0, null);
    }

    public static void showInfo(View rltView, @StringRes int title) {
        showInfo(rltView, title, Snackbar.LENGTH_SHORT, COLOR_CONTENT_TEXT, COLOR_BACK_GROUND, 0, null, null, 0, null);
    }

    public static void showLoadErrorInfo(View rltView, final OnRetryListener listener) {
        final Snackbar snackbar = Snackbar.make(rltView, "加载失败", Snackbar.LENGTH_INDEFINITE);
        showInfo(snackbar, COLOR_CONTENT_TEXT, COLOR_BACK_GROUND,
                COLOR_ACTION_TEXT, "重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onRetry();
                        snackbar.dismiss();
                    }
                }, 0, null);
    }

    public static void showNoNetInfo(View rltView) {
        final Snackbar snackbar = Snackbar.make(rltView, "网络连接不可用", Snackbar.LENGTH_INDEFINITE);
        showInfo(snackbar, COLOR_CONTENT_TEXT, COLOR_BACK_GROUND, COLOR_ACTION_TEXT, "设置",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NetWorkHelper.openWirelessSettings(view.getContext());
                        snackbar.dismiss();
                    }
                }, 0, null);
    }

    public static Snackbar showInfo(View rltView, @StringRes int titleRes, int duration, @ColorRes int textColor, @ColorRes int backgroundColor, @ColorRes int actionTextColor,
                                    String content, View.OnClickListener listener, int gravity, View iconView) {
        Snackbar snackbar = Snackbar.make(rltView, titleRes, duration);
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
        return snackbar;
    }

    public static Snackbar showInfo(View rltView, String title, int duration, @ColorRes int textColor, @ColorRes int backgroundColor, @ColorRes int actionTextColor,
                                String content, View.OnClickListener listener, int gravity, View iconView) {
        Snackbar snackbar = Snackbar.make(rltView, title, duration);
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
        return snackbar;
    }

    public static Snackbar showInfo(Snackbar snackbar, @ColorRes int textColor, @ColorRes int backgroundColor, @ColorRes int actionTextColor,
                                    String content, View.OnClickListener listener, int gravity, View iconView) {
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
        return snackbar;
    }

    public interface OnRetryListener {
        void onRetry();
    }
}
