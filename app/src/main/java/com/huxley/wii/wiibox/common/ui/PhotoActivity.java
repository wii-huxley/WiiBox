package com.huxley.wii.wiibox.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.Window;
import android.widget.ImageView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiitools.base.BaseActivity;

public class PhotoActivity extends BaseActivity {

    private String mUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_photo;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);

        //设置允许通过ActivityOptions.makeSceneTransitionAnimation发送或者接收Bundle
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        //设置使用TransitionManager进行动画，不设置的话系统会使用一个默认的TransitionManager
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //退出变换(ExitTransition)决定了A调用B的时候，A中的View是如何播放动画的
        getWindow().setExitTransition(new Fade());
        //进入变换(EnterTransition)决定了A调用B的时候，B中的View是如何播放动画的
        getWindow().setEnterTransition(new Fade());
        //返回变换(ReturnTransition)决定了在B返回A的时候，B中的View是如何播放动画的
        getWindow().setReturnTransition(new Fade());
        //再次进入变换(ReenterTransition)决定了在B返回A的时候，A中的View是如何播放动画的
        getWindow().setReenterTransition(new Fade());
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        ImageView photoView = $(R.id.photoView);
        ViewCompat.setTransitionName(photoView, mUrl);
        ImageLoaderUtils.setBigImage(photoView, mUrl);
    }

    @Override
    protected void handleIntent(Intent intent) {
        mUrl = intent.getStringExtra(Constant.Key.URL);
    }
}
