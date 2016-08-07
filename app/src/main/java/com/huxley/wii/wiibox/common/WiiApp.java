package com.huxley.wii.wiibox.common;

import com.baidu.tiebasdk.TiebaSDK;
import com.huxley.wii.wiibox.common.helper.InitHelper;
import com.huxley.wii.wiitools.base.WiiApplication;
import com.huxley.wii.wiitools.common.WiiTools;


/**
 *
 * Created by huxley on 16/2/28.
 */
public class WiiApp extends WiiApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        InitHelper.init(getContext());
        WiiTools.init(getContext()).debug();
        TiebaSDK.init(this);
    }

}