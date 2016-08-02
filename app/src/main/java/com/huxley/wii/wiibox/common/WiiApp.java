package com.huxley.wii.wiibox.common;

import com.huxley.wii.wiibox.common.helper.InitHelper;
import com.huxley.wii.wiitools.common.WiiTools;
import com.huxley.wii.wiitools.base.WiiApplication;


/**
 * Created by huxley on 16/2/28.
 */
public class WiiApp extends WiiApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        InitHelper.init(getContext());
        WiiTools.init(getContext()).debug();
    }

}