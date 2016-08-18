package com.huxley.wii.wiibox.page.codekk.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class CodekkDetailActivity extends BaseActivity {

    private String id;
    private String name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_codekk_detail;
    }

    @Override
    protected void handleIntent(Intent intent) {
        name = intent.getStringExtra(Constant.Key.NAME);
        id = intent.getStringExtra(Constant.Key.ID);
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        Toolbar toolbar = UIHelper.createToolbar(this, name);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        CodekkDetailFragment mCodekkDetailFragment = (CodekkDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (mCodekkDetailFragment == null) {
            mCodekkDetailFragment = CodekkDetailFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mCodekkDetailFragment, R.id.fragmentContent);
        }
        new CodekkDetailPresenter(mCodekkDetailFragment, id);
    }
}
