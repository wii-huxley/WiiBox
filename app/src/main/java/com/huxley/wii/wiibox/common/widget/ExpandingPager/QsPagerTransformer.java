package com.huxley.wii.wiibox.common.widget.ExpandingPager;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Qs on 16/5/30.
 */
public class QsPagerTransformer implements ViewPager.PageTransformer {

    public static final float MAX_SCALE = 0.9f;
    public static final float MIN_SCALE = 0.8f;

    @Override
    public void transformPage(View page, float position) {

        position = position < -1 ? -1 : position;
        position = position > 1 ? 1 : position;

        float tempScale = position < 0 ? 1 + position : 1 - position;

        float slope = (MAX_SCALE - MIN_SCALE) / 1;
        float scaleValue = MIN_SCALE + tempScale * slope;
        ViewCompat.setScaleX(page, scaleValue);
        ViewCompat.setScaleY(page, scaleValue);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            page.getParent().requestLayout();
        }
    }
}
