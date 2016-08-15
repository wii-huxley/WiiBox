package com.huxley.wii.wiibox.mvp.knowledge;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class KnowledgeFragment extends BaseFragment implements KnowledgeContract.View {

    private KnowledgeContract.Presenter mKnowledgePresenter;

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

    }
}
