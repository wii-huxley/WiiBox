package com.huxley.wii.wiibox.mvp.main.gank;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 * Created by huxley on 16/7/5.
 */
public interface GankContract {

    interface View extends BaseView<Presenter> , INetView<List<GankInfo>> {

    }

    interface Presenter extends BasePresenter {

        void loadGank(boolean isRefresh);
    }
}
