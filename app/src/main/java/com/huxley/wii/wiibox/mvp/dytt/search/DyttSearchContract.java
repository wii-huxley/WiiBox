package com.huxley.wii.wiibox.mvp.dytt.search;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 * Created by huxley on 16/7/13.
 */
public interface DyttSearchContract {

    interface View extends BaseView<Presenter>, INetView<List<DyttListBean.MovieInfo>> {
    }

    interface Presenter extends BasePresenter {

        void search(String content);

        void loadMore();

        void reTry();
    }
}
