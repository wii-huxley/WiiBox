package com.huxley.wii.wiibox.mvp.loginRegister.model;

import com.huxley.wii.wiitools.common.Utils.CodeUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LeiJin01 on 2016/8/12.
 */
public class RegisterModel {

    public RegisterModel() {
    }

    public void register(String userName, String password, SaveListener<UserInfo> listener) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userName);
        userInfo.setPassword(CodeUtils.passwordEncoder(password));
        userInfo.signUp(listener);
    }
}
