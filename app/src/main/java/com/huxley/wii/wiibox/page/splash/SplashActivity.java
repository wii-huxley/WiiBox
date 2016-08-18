package com.huxley.wii.wiibox.page.splash;

import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiitools.base.BaseActivity;


public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        UIHelper.jumpSplash(this);
    }
}
