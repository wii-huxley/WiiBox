package com.huxley.wii.wiitools.common.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.huxley.wii.wiitools.R;
import com.huxley.wii.wiitools.view.WiiTextView;

/**
 *
 * Created by huxley on 16/7/3.
 */
public class UIHelper {

    private static final int INVALID = -1;

    public static WiiTextView getTextView(Context context) {
        return new WiiTextView(context)
                .setWiiTextColor(R.color.color_grey_900)
                .setWiiTextSize(R.dimen.sp_18);
    }

    public static View getView(Context context, int resourceId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (resourceId != INVALID) {
            return inflater.inflate(resourceId, null);
        }
        return null;
    }
}
