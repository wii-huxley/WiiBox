package com.huxley.wii.wiibox.page.knowledge;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiibox.page.knowledge.model.KnowledgeBean;

import java.util.ArrayList;

public interface KnowledgeContract {

    interface View extends BaseView<Presenter> {
        void showContent(ArrayList<KnowledgeBean> knowledgeBeans);
    }

    interface Presenter extends BasePresenter {
        void getKnowledgeContent(String path);
    }
}
