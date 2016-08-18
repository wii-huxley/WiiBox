package com.huxley.wii.wiibox.page.main.gank.detail;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 * Created by huxley on 16/7/15.
 */
public interface GankDataDetailContract {

    interface View extends BaseView<Presenter> ,INetView<List<Object>> {

        void showTitle(String date);
    }

    interface Presenter extends BasePresenter {

        void loadData();
    }
}
