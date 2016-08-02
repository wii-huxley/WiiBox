package com.huxley.wii.wiibox.common.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.huxley.wii.wiibox.common.WiiApp;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by huxley on 16/3/3.
 */
public class Tools {

    /**
     * @return 当前的类名（全名）
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1]; // 这个数字由实际情况来定
        result = thisMethodStack.getClassName();
        return result;
    }

    /**
     * log这个方法就可以显示超链
     */
    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result += thisMethodStack.getClassName()+ ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

    /**
     * 判断是否有虚拟按键
     * @param ctx
     * @return
     */
    public static boolean hasSoftKeys(Context ctx){
        boolean hasSoftwareKeys;
        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Display d = manager.getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasSoftwareKeys =  (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        }else{
            boolean hasMenuKey = ViewConfiguration.get(ctx).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            hasSoftwareKeys = !hasMenuKey && !hasBackKey;
        }
        return hasSoftwareKeys;
    }


    private static long lastClickTime;

    /**
     * 判断是否是快速点击
     * @return true:快速点击
     */
    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time-lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    protected static ShapeDrawable make(int size, int color) {
        ShapeDrawable indicator = new ShapeDrawable(new OvalShape());
        indicator.setIntrinsicWidth(size);
        indicator.setIntrinsicHeight(size);
        indicator.getPaint().setColor(color);
        return indicator;
    }

    /**
     * A method for animating width for the tabs.
     * 执行该动画通过LayoutParams动态改变BottomTabs的宽高
     * @param tab tab to animate.
     * @param start starting width.
     * @param end final width after animation.
     */
    public static void resizeTab(final View tab, float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                ViewGroup.LayoutParams params = tab.getLayoutParams();
                if (params == null) return;

                /***
                 * 1. Math.ceil()用作向上取整。
                 * 2. Math.floor()用作向下取整。
                 * 3. Math.round() 我们数学中常用到的四舍五入取整。
                 */
                params.width = Math.round((float) animator.getAnimatedValue());
                tab.setLayoutParams(params);
            }
        });
        animator.start();
    }

    /**
     * Animate a background color change. Uses Circular Reveal if supported,
     * otherwise crossfades the background color in.
     * 设备支持（API21）圆形扩散波纹，就用这种方式改变背景，否则就通过淡入淡出背景色的方式
     *
     * 触摸点击view
     * @param clickedView    the view that was clicked for calculating the start position for the Circular Reveal.
     * 当前展示的背景色
     * @param backgroundView the currently showing background color.
     * 覆盖后的背景色
     * @param bgOverlay      the overlay view for the new background color that will be
     *                       animated in.
     * 新的颜色
     * @param newColor       the new color.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateBGColorChange(View clickedView, final View backgroundView,
                                               final View bgOverlay, final int newColor) {
        int centerX = (int) (ViewCompat.getX(clickedView) + (clickedView.getMeasuredWidth() / 2));
        int centerY = clickedView.getMeasuredHeight() / 2;
        int finalRadius = backgroundView.getWidth();

        backgroundView.clearAnimation();
        bgOverlay.clearAnimation();

        Object animator;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!bgOverlay.isAttachedToWindow()) {
                return;
            }

            animator = ViewAnimationUtils
                    .createCircularReveal(bgOverlay, centerX, centerY, 0, finalRadius);
        } else {
            ViewCompat.setAlpha(bgOverlay, 0);
            animator = ViewCompat.animate(bgOverlay).alpha(1);
        }

        if (animator instanceof ViewPropertyAnimatorCompat) {
            ((ViewPropertyAnimatorCompat) animator).setListener(new ViewPropertyAnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(View view) {
                    onCancel();
                }

                @Override
                public void onAnimationCancel(View view) {
                    onCancel();
                }

                private void onCancel() {
                    backgroundView.setBackgroundColor(newColor);
                    bgOverlay.setVisibility(View.INVISIBLE);
                    ViewCompat.setAlpha(bgOverlay, 1);
                }
            }).start();
        } else if (animator != null) {
            ((Animator) animator).addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    onCancel();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    onCancel();
                }

                private void onCancel() {
                    backgroundView.setBackgroundColor(newColor);
                    bgOverlay.setVisibility(View.INVISIBLE);
                    ViewCompat.setAlpha(bgOverlay, 1);
                }
            });

            ((Animator) animator).start();
        }

        bgOverlay.setBackgroundColor(newColor);
        bgOverlay.setVisibility(View.VISIBLE);
    }

    protected static int getColor(int color) {
        TypedValue tv = new TypedValue();
        WiiApp.getContext().getTheme().resolveAttribute(com.roughike.bottombar.R.attr.colorPrimary, tv, true);
        return tv.data;
    }
}
