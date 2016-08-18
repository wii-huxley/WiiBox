package com.huxley.wii.wiibox.page.dytt.detail;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiibox.page.dytt.model.DyttDetailInfo;
import com.huxley.wii.wiitools.base.net.INetView;

public interface DyttDetailContract {

    interface View extends BaseView<Presenter>, INetView<DyttDetailInfo> {
        void setTitle(String title);
    }

    interface Presenter extends BasePresenter {
    }
}
