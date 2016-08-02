package com.huxley.wii.wiibox.listener;

import android.view.View;
import android.widget.AdapterView;

import com.huxley.wii.wiibox.common.utils.Tools;


/**
 * Created by huxley on 16/3/9.
 */
public class DoItemClickListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Tools.isFastClick()) {
            return;
        }
        doItemClick(parent, view, position, id);
    }

    public void doItemClick(AdapterView<?> parent, View view, int position, long id) {}
}
