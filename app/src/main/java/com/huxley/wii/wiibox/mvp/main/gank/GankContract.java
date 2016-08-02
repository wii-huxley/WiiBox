package com.huxley.wii.wiibox.mvp.main.gank;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;

import java.util.List;

/**
 * Created by huxley on 16/7/5.
 */
public interface GankContract {

    interface View extends BaseView<Presenter> {

        void isEmptyView();

        void isContentView(List<GankInfo> gankList, boolean isFirst);

        void isErrorView();

        void isNoNetView();

        void setProgress(boolean isShow);
    }

    interface Presenter extends BasePresenter {

        void loadGank(boolean isRefresh);
    }
}
