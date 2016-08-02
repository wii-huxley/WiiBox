package com.huxley.wii.wiibox.mvp.dytt.detail;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttDetailInfo;

public interface DyttDetailContract {

    interface View extends BaseView<Presenter> {
        void setContent(DyttDetailInfo movieDetailInfo);
    }

    interface Presenter extends BasePresenter {
        void loadData(String url);
    }
}
