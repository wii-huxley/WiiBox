package com.huxley.wii.wiibox.mvp.user;

import android.support.annotation.NonNull;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/8/13.
 */
public class UserPresenter implements UserContract.Presenter {

    private final UserContract.View mUserView;

    public UserPresenter(@NonNull UserContract.View view) {
        mUserView = checkNotNull(view);
        mUserView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}