package com.huxley.wii.wiibox.mvp.ting56.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class TingPlayActivity extends BaseActivity {

    private String url;
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chapter_list;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(Constant.Extra.URL);
        title = intent.getStringExtra(Constant.Extra.NAME);
    }

    private void initView() {
        Toolbar toolbar = $(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        TingPlayFragment tingPlayFragment = (TingPlayFragment) getSupportFragmentManager().findFragmentById(R.id.chapterFragmentContent);
        if (tingPlayFragment == null) {
            tingPlayFragment = TingPlayFragment.newInstance(url);
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), tingPlayFragment, R.id.chapterFragmentContent);
        }
        new TingPlayPresenter(tingPlayFragment);
    }
}
