package com.huxley.wii.wiibox.listener;

import android.view.View;
import android.view.View.OnClickListener;

import com.huxley.wii.wiibox.common.utils.Tools;

/**
 * Created by huxley on 16/3/9.
 */
public abstract class DoClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
        if (Tools.isFastClick()) {
            return;
        }
        doClick(v);
    }

    public void doClick(View v){};
}
