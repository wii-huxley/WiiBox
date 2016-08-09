package com.huxley.wii.wiibox.mvp.codekk;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 * Created by huxley on 16/7/31.
 */
public interface CodekkContract {

    interface View extends BaseView<Presenter>, INetView<List<CodekkProjectBean>> {
        void clearContent();
    }

    interface Presenter extends BasePresenter {

        void loadMore();

        void refresh();

        void search(String content, boolean isFirst);

        void resetContent();

        void reTry();

        boolean hasMore();

        void clearContent();
    }
}