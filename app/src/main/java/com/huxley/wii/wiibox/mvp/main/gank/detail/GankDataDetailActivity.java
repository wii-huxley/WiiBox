package com.huxley.wii.wiibox.mvp.main.gank.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.Window;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class GankDataDetailActivity extends BaseActivity {


    public static final String TRANSIT_PIC = "picture";
    public static final String EXTRA_GANK_INFO = "gankInfo";
    public static final String EXTRA_POSITION = "position";
    private int position;
    private GankInfo mGankInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank_data_detail;
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
        super.created(savedInstanceState);

        GankDataDetailFragment gankDataDetailFragment = (GankDataDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (gankDataDetailFragment == null) {
            gankDataDetailFragment = GankDataDetailFragment.newInstance(mGankInfo);
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), gankDataDetailFragment, R.id.flContent);
        }

        new GankDataDetailPresenter(gankDataDetailFragment);
    }

    @Override
    protected boolean back(int keyCode, KeyEvent event) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_POSITION, position));
        return super.back(keyCode, event);
    }

    @Override
    protected void handleIntent(Intent intent) {
        position = mIntent.getIntExtra(EXTRA_POSITION, -1);
        mGankInfo = (GankInfo) mIntent.getSerializableExtra(EXTRA_GANK_INFO);
    }

    public static Intent newIntent(Context context, GankInfo gankInfo, int position) {
        Intent intent = new Intent(context, GankDataDetailActivity.class);
        intent.putExtra(EXTRA_GANK_INFO, gankInfo);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }
}
