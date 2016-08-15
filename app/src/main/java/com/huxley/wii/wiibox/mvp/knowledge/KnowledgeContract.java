package com.huxley.wii.wiibox.mvp.knowledge;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;

public interface KnowledgeContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
