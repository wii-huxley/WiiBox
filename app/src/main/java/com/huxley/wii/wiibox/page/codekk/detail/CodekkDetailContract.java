package com.huxley.wii.wiibox.page.codekk.detail;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiitools.base.net.INetView;

public interface CodekkDetailContract {

    interface View extends BaseView<Presenter>, INetView<String> {
    }

    interface Presenter extends BasePresenter {
    }
}
