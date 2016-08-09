package com.huxley.wii.wiibox.mvp.dytt;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 * DyttContract
 * Created by huxley on 16/7/5.
 */
public interface DyttContract {

    interface View extends BaseView<Presenter>, INetView<List<DyttListBean.MovieInfo>>{

    }

    interface Presenter extends BasePresenter {

        void refresh();

        void loadMore();

        void reTry();
    }
}
