package com.huxley.wii.wiibox.mvp.main.gank.detail;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;

import java.util.List;

/**
 * Created by huxley on 16/7/15.
 */
public interface GankDataDetailContract {

    interface View extends BaseView<Presenter> {

        void isEmptyView();

        void isContentView(List<Object> gankInfo);

        void isErrorView();

        void isNoNetView();

        void setProgress(boolean isShow);
    }

    interface Presenter extends BasePresenter {

        void loadData(String date);
    }
}
