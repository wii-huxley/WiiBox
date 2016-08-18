package com.huxley.wii.wiibox.page.loginRegister.model;

import com.huxley.wii.wiitools.common.Utils.CodeUtils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
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

    public void requestSMSCode(String phoneNum, QueryListener<Integer> listener) {
        BmobSMS.requestSMSCode(phoneNum, "WiiBox", listener);
    }

    public void signOrLoginByMobilePhone(String phoneNum, String code, LogInListener<UserInfo> listener) {
        UserInfo.signOrLoginByMobilePhone(phoneNum, code, listener);
    }
}
