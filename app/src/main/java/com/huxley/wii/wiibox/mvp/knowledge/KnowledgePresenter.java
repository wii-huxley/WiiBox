package com.huxley.wii.wiibox.mvp.knowledge;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeBean;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeModel;
import com.huxley.wii.wiitools.common.Utils.L;

import java.util.ArrayList;

import rx.Subscriber;

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
        mModel.getKnowledges().subscribe(new Subscriber<ArrayList<KnowledgeBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<KnowledgeBean> knowledgeBeans) {
                mKnowledgeView.showContent(knowledgeBeans);
            }
        });
    }

    @Override
    public void getKnowledgeContent(String path) {
        mModel.getKnowledgeContent(path).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                L.i(s);
            }
        });
    }
}
