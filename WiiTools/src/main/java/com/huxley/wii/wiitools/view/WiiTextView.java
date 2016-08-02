package com.huxley.wii.wiitools.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.huxley.wii.wiitools.common.helper.ResourcesHelper;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/4.
 */
public class WiiTextView extends TextView {

    public WiiTextView(Context context) {
        this(context, null);
    }

    public WiiTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WiiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WiiTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WiiTextView setWiiText(CharSequence text) {
        setText(checkNotNull(text));
        return this;
    }

    public WiiTextView setWiiTextColor(@ColorRes int colorRes) {
        setTextColor(ResourcesHelper.getColor(colorRes));
        return this;
    }

    public WiiTextView setWiiTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public WiiTextView setWiiTextSize(@DimenRes int dimenRes) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourcesHelper.getDimension(dimenRes));
        return this;
    }
}
