package com.huxley.wii.wiibox.page.loginRegister.login;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiitools.base.net.INetView;

/**
 * Created by LeiJin01 on 2016/8/11.
 */
public interface LoginContact {

    interface View extends BaseView<Presenter>, INetView<Object>{
        void animateRevealShow();
    }

    interface Presenter extends BasePresenter{

    }
}
