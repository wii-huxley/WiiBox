package com.huxley.wii.wiibox.common.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiitools.base.BaseActivity;

public class PhotoActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_photo;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        ImageLoaderUtils.setBigImage((ImageView) $(R.id.photoView), getIntent().getStringExtra(Constant.Extra.URL));
    }
}
