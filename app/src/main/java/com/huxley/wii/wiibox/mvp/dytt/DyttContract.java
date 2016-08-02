package com.huxley.wii.wiibox.mvp.dytt;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;

import java.util.List;

/**
 * Created by huxley on 16/7/5.
 */
public interface DyttContract {

    interface View extends BaseView<Presenter> {

        void isNoNetView(boolean isFirst);

        void isEmptyView(boolean isFirst);

        void isErrorView(boolean isFirst);

        void setProgress(boolean isShow);

        void setContent(List<DyttListBean.MovieInfo> movieInfos, boolean isRefresh, boolean isFirst);
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void loadMore();
    }
}
