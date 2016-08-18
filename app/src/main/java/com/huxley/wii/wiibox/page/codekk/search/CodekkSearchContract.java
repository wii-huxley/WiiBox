package com.huxley.wii.wiibox.page.codekk.search;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiibox.page.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

public interface CodekkSearchContract {

    interface View extends BaseView<Presenter>, INetView<List<CodekkProjectBean>> {
        void clearContent();
    }

    interface Presenter extends BasePresenter {

        void search(String content, boolean isFirst);

        void loadMore();

        void reTry();

        boolean hasMore();
    }
}
