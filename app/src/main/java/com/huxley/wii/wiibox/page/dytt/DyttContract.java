package com.huxley.wii.wiibox.page.dytt;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiibox.page.dytt.model.DyttListBean;
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
