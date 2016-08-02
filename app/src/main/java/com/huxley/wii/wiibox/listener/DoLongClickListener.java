package com.huxley.wii.wiibox.listener;

import android.view.View;

/**
 * Created by huxley on 16/3/10.
 */
public abstract class DoLongClickListener implements View.OnLongClickListener {
    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
