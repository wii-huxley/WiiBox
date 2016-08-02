package com.huxley.wii.wiitools.base;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.huxley.wii.wiitools.common.Utils.L;


/**
 * 生命周期：
 *  onCreate –> onContentChanged –> onStart –> onPostCreate –> onResume –> onPostResume –> onPause –> onStop –> onDestroy
 *
 * Created by huxley on 16/2/28.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected View view;
    protected LayoutInflater mInflater;
    protected Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //写死竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        create(savedInstanceState);

        mIntent = getIntent();
        mInflater = getLayoutInflater();
        view = mInflater.inflate(getLayoutId(), null);

        //设置contentView
        setContentView(view);
        //处理Intent(主要用来获取其中携带的参数)
        if (getIntent() != null){
            handleIntent(getIntent());
        }
        created(savedInstanceState);
        L.i("onCreate");
    }

    protected void handleIntent(Intent intent) {

    }

    protected abstract int getLayoutId();

    protected void create(Bundle savedInstanceState){
    }

    protected void created(Bundle savedInstanceState){
    }

    /**
     * 当Activity的布局改动时，即setContentView()或者addContentView()方法执行完毕时就会调用该方法
     * 各种View的findViewById()方法都可以放到该方法中
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        L.i("onContentChanged");
    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        L.i("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.i("onRestart");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        L.i("onPostCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        L.i("onPostResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.i("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.i("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.i("onDestroy");
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        L.i("onConfigurationChanged");
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        L.i("onSaveInstanceState");
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
    }

    public <V extends View> V $(@IdRes @LayoutRes int id) {
        return (V) view.findViewById(id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return back(keyCode, event);
            case KeyEvent.KEYCODE_HOME:
                home();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean back(int keyCode, KeyEvent event) {
        L.i("back");
        return super.onKeyDown(keyCode, event);
    }

    protected void home() {
        L.i("home");
    }

}
