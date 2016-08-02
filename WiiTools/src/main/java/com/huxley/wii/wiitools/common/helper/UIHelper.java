package com.huxley.wii.wiitools.common.helper;

import android.content.Context;

import com.huxley.wii.wiitools.R;
import com.huxley.wii.wiitools.view.WiiTextView;

/**
 *
 * Created by huxley on 16/7/3.
 */
public class UIHelper {

    public static WiiTextView getTextView(Context context) {
        return new WiiTextView(context)
                .setWiiTextColor(R.color.color_grey_900)
                .setWiiTextSize(R.dimen.sp_18);
    }
}
