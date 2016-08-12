package com.huxley.wii.wiibox.mvp.loginRegister.register;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.loginRegister.model.UserInfo;
import com.huxley.wii.wiitools.base.net.INetView;

/**
 *
 * Created by LeiJin01 on 2016/8/11.
 */
public interface RegisterContact {

    interface View extends BaseView<Presenter>, INetView<UserInfo>{

    }

    interface Presenter extends BasePresenter{

        void register(String userName, String password);

        void requestSMSCode(String phoneNum);

        void signOrLoginByMobilePhone(String phoneNum, String code);
    }
}
