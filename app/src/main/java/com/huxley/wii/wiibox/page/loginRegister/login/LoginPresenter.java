package com.huxley.wii.wiibox.page.loginRegister.login;

import android.support.annotation.NonNull;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by LeiJin01 on 2016/8/11.
 */
public class LoginPresenter implements LoginContact.Presenter{

    private LoginContact.View mView;

    public LoginPresenter(@NonNull LoginContact.View view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
