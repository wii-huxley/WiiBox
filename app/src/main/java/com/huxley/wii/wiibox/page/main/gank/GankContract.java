package com.huxley.wii.wiibox.page.main.gank;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiibox.page.main.gank.model.GankInfo;
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
