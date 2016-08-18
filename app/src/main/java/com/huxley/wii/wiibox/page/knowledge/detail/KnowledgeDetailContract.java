package com.huxley.wii.wiibox.page.knowledge.detail;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;

public interface KnowledgeDetailContract {

    interface View extends BaseView<Presenter> {
        void setContent(String content);
    }

    interface Presenter extends BasePresenter {
    }
}
