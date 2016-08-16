package com.huxley.wii.wiibox.mvp.knowledge.knowledgedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeBean;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeModel;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class KnowledgeDetailActivity extends BaseActivity {

    private KnowledgeBean knowledgeBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void handleIntent(Intent intent) {
        knowledgeBean = (KnowledgeBean) intent.getSerializableExtra(Constant.Key.DATA);
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
    }

    private void initView() {
        Toolbar toolbar = UIHelper.createToolbar(this, knowledgeBean.name);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        KnowledgeDetailFragment mKnowledgeDetailFragment = (KnowledgeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (mKnowledgeDetailFragment == null) {
            mKnowledgeDetailFragment = KnowledgeDetailFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mKnowledgeDetailFragment, R.id.fragmentContent);
        }
        new KnowledgeDetailPresenter(mKnowledgeDetailFragment, knowledgeBean, new KnowledgeModel());
    }
}
