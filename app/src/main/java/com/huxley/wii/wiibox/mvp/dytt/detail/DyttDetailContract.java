package com.huxley.wii.wiibox.mvp.dytt.detail;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttDetailInfo;
import com.huxley.wii.wiitools.base.net.INetView;

public interface DyttDetailContract {

    interface View extends BaseView<Presenter>, INetView<DyttDetailInfo> {
        void setTitle(String title);
    }

    interface Presenter extends BasePresenter {
    }
}
