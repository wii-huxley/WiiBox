package com.huxley.wii.wiibox.mvp.dytt.search;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;

import java.util.List;

/**
 * Created by huxley on 16/7/13.
 */
public interface DyttSearchContract {

    interface View extends BaseView<Presenter> {

        void isNoNetView(boolean isFirst);

        void isEmptyView(boolean isFirst);

        void isErrorView(boolean isFirst);

        void setProgress(boolean isShow);

        void setContent(boolean isRefresh, boolean isFirst, List<DyttListBean.MovieInfo> movieInfos);
    }

    interface Presenter extends BasePresenter {
        void search(String content);

        void loadMore();
    }
}
