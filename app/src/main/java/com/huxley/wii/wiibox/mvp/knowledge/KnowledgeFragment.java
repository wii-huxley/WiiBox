package com.huxley.wii.wiibox.mvp.knowledge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.widget.treeview.adapter.TreeListViewAdapter;
import com.huxley.wii.wiibox.common.widget.treeview.bean.Node;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeAdapter;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeBean;
import com.huxley.wii.wiitools.base.BaseFragment;

import java.util.ArrayList;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class KnowledgeFragment extends BaseFragment implements KnowledgeContract.View, TreeListViewAdapter.OnTreeNodeClickListener {

    private KnowledgeContract.Presenter mKnowledgePresenter;
    private KnowledgeAdapter            mAdapter;
    private ListView                    knowledgeTree;
    private ArrayList<KnowledgeBean>    knowledgeBeans;

    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }

    @Override
    public void setPresenter(@NonNull KnowledgeContract.Presenter presenter) {
        mKnowledgePresenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        mKnowledgePresenter.start();
    }

    private void initView() {
        UIHelper.createToolbar((AppCompatActivity) getActivity(), rootView, R.string.knowledge_title);

        knowledgeTree = $(R.id.lvMarkDown);
    }

    @Override
    public void showContent(ArrayList<KnowledgeBean> knowledgeBeans) {
        try {
            this.knowledgeBeans = knowledgeBeans;
            knowledgeTree.setAdapter(mAdapter = new KnowledgeAdapter(knowledgeTree, getContext(), knowledgeBeans, 1));
            mAdapter.setOnTreeNodeClickListener(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(Node node, int position) {
        if (node.isLeaf()) {
            for (KnowledgeBean knowledgeBean : knowledgeBeans) {
                if (knowledgeBean.id == node.id) {
                    UIHelper.startKnowledgeDetailActivity(getContext(), knowledgeBean);
                    break;
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
