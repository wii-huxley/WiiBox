package com.huxley.wii.wiibox.mvp.loginRegister.register;

import android.support.annotation.NonNull;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by LeiJin01 on 2016/8/11.
 */
public class RegisterPresenter implements RegisterContact.Presenter{

    private RegisterContact.View mView;

    public RegisterPresenter(@NonNull RegisterContact.View view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
