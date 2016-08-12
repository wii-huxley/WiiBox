package com.huxley.wii.wiibox.mvp.loginRegister.model;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LeiJin01 on 2016/8/12.
 */
public class LoginModel {

    private static LoginModel instance;

    private LoginModel() {
    }

    public static LoginModel getInstance() {
        if (instance == null) {
            synchronized (LoginModel.class) {
                if (instance == null) {
                    instance = new LoginModel();
                }
            }
        }
        return instance;
    }

    public void login(UserInfo userInfo) {
        userInfo.signUp(new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo s, BmobException e) {

            }
        });
    }
}
