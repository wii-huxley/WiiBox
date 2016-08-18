package com.huxley.wii.wiibox.page.main.androidtools.bezier;

import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class BezierActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        BezierFragment mBezierFragment = (BezierFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (mBezierFragment == null) {
            mBezierFragment = BezierFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mBezierFragment, R.id.fragmentContent);
        }
        new BezierPresenter(mBezierFragment);
    }
}
