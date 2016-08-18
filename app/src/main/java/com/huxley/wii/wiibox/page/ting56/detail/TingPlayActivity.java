package com.huxley.wii.wiibox.page.ting56.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.helper.UIHelper;
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

        initView();
    }

    @Override
    protected void handleIntent(Intent intent) {
        url = intent.getStringExtra(Constant.Key.URL);
        title = intent.getStringExtra(Constant.Key.NAME);
    }

    private void initView() {
        Toolbar toolbar = UIHelper.createToolbar(this, title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> finish());

        TingPlayFragment tingPlayFragment = (TingPlayFragment) getSupportFragmentManager().findFragmentById(R.id.chapterFragmentContent);
        if (tingPlayFragment == null) {
            tingPlayFragment = TingPlayFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), tingPlayFragment, R.id.chapterFragmentContent);
        }
        new TingPlayPresenter(tingPlayFragment, url);
    }
}