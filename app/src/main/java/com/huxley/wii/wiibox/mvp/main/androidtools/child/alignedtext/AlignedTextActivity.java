package com.huxley.wii.wiibox.mvp.main.androidtools.child.alignedtext;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class AlignedTextActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aligned_text;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("StepView");
            toolbar.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        AlignedTextFragment alignedTextFragment = (AlignedTextFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);

        if (alignedTextFragment == null) {
            alignedTextFragment = AlignedTextFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), alignedTextFragment, R.id.contentFragment);
        }

        new AlignedTextPresenter(alignedTextFragment);
    }
}
