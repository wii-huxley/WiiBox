package com.huxley.wii.wiibox.mvp.loginRegister.login;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
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
