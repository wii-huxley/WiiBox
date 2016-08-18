package com.huxley.wii.wiibox.page.codekk;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiibox.page.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 * Created by huxley on 16/7/31.
 */
public interface CodekkContract {

    interface View extends BaseView<Presenter>, INetView<List<CodekkProjectBean>> {
    }

    interface Presenter extends BasePresenter {

        void loadMore();

        void refresh();

        void reTry();

        boolean hasMore();
    }
}