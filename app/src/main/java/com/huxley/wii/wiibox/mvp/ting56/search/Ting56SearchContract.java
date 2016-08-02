package com.huxley.wii.wiibox.mvp.ting56.search;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;

import java.util.List;

/**
 * Created by huxley on 16/7/13.
 */
public interface Ting56SearchContract {

    interface View extends BaseView<Presenter> {

        void isNoNetView(boolean isFirst);

        void isEmptyView(boolean isFirst);

        void isErrorView(boolean isFirst);

        void setProgress(boolean isShow);

        void setContent(boolean isRefresh, boolean isFirst, List<Ting56Bean> ting56DetailBeen);
    }

    interface Presenter extends BasePresenter {
        void search(String content);

        void loadMore();
    }
}
