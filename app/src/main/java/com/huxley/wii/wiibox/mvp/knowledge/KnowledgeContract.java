package com.huxley.wii.wiibox.mvp.knowledge;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.knowledge.model.KnowledgeBean;

import java.util.ArrayList;

public interface KnowledgeContract {

    interface View extends BaseView<Presenter> {
        void showContent(ArrayList<KnowledgeBean> knowledgeBeans);
    }

    interface Presenter extends BasePresenter {
        void getKnowledgeContent(String path);
    }
}
