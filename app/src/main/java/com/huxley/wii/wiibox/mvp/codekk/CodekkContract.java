package com.huxley.wii.wiibox.mvp.codekk;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkProjectBean;

import java.util.List;

/**
 * Created by huxley on 16/7/31.
 */
public interface CodekkContract {

    interface View extends BaseView<Presenter> {

        void isNoNetView(boolean isFirst);

        void isEmptyView(boolean isFirst);

        void isErrorView(boolean isFirst);

        void setProgress(boolean isShow);

        void setContent(List<CodekkProjectBean> codekkProjectBean, boolean isRefresh, boolean isFirst);
    }

    interface Presenter extends BasePresenter {

        void loadMore();

        void refresh();
    }
}