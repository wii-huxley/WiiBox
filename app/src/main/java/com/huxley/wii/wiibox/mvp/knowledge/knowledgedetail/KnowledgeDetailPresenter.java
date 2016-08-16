package com.huxley.wii.wiibox.mvp.knowledge.knowledgedetail;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeBean;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeModel;
import com.huxley.wii.wiitools.common.Utils.L;

import rx.Subscriber;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class KnowledgeDetailPresenter implements KnowledgeDetailContract.Presenter {

    private final KnowledgeDetailContract.View mKnowledgeDetailView;
    private KnowledgeBean knowledgeBean;
    private KnowledgeModel model;

    public KnowledgeDetailPresenter(@NonNull KnowledgeDetailContract.View view, KnowledgeBean knowledgeBean, KnowledgeModel model) {
        mKnowledgeDetailView = checkNotNull(view);
        this.knowledgeBean = checkNotNull(knowledgeBean);
        this.model = checkNotNull(model);
        mKnowledgeDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        model.getKnowledgeContent(knowledgeBean.path).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String s) {
                mKnowledgeDetailView.setContent(s);
                L.i(s);
            }
        });
    }
}
