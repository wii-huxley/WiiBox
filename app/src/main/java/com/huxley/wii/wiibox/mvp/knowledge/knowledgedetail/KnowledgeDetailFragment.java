package com.huxley.wii.wiibox.mvp.knowledge.knowledgedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.zzhoujay.richtext.RichText;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class KnowledgeDetailFragment extends BaseFragment implements KnowledgeDetailContract.View {

    private KnowledgeDetailContract.Presenter mKnowledgeDetailPresenter;
    private TextView tvContent;

    public static KnowledgeDetailFragment newInstance() {
        return new KnowledgeDetailFragment();
    }

    @Override
    public void setPresenter(@NonNull KnowledgeDetailContract.Presenter presenter) {
        mKnowledgeDetailPresenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_detail;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        mKnowledgeDetailPresenter.start();
    }

    private void initView() {
        tvContent = $(R.id.tvContent);
    }

    @Override
    public void setContent(String content) {
        tvContent.post(() -> RichText.fromMarkdown(content).into(tvContent));
    }
}
