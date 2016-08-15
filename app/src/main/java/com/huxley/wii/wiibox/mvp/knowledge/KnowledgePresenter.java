package com.huxley.wii.wiibox.mvp.knowledge;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeModel;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class KnowledgePresenter implements KnowledgeContract.Presenter {

    private final KnowledgeContract.View mKnowledgeView;
    private KnowledgeModel mModel;

    public KnowledgePresenter(@NonNull KnowledgeContract.View view, KnowledgeModel model) {
        mKnowledgeView = checkNotNull(view);
        mModel = checkNotNull(model);
        mKnowledgeView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
