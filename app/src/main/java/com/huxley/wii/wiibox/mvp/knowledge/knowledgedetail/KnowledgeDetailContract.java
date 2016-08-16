package com.huxley.wii.wiibox.mvp.knowledge.knowledgedetail;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;

public interface KnowledgeDetailContract {

    interface View extends BaseView<Presenter> {
        void setContent(String content);
    }

    interface Presenter extends BasePresenter {
    }
}
