package com.huxley.wii.wiibox.page.main.androidtools.bezier;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class BezierActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.wii_activity_base_title;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        Toolbar toolbar = UIHelper.createToolbar(this, R.string.bezier_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        BezierFragment mBezierFragment = (BezierFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (mBezierFragment == null) {
            mBezierFragment = BezierFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mBezierFragment, R.id.fragmentContent);
        }
        new BezierPresenter(mBezierFragment);
    }
}
