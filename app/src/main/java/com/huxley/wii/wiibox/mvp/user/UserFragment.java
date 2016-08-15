package com.huxley.wii.wiibox.mvp.user;/**
 * Created by huxley on 16/8/13.
 */

import android.support.annotation.NonNull;

import com.huxley.wii.wiitools.base.BaseFragment;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class UserFragment extends BaseFragment implements UserContract.View {

    private UserContract.Presenter mUserPresenter;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void setPresenter(@NonNull UserContract.Presenter presenter) {
        mUserPresenter = checkNotNull(presenter);
    }

}
