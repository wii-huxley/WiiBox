package com.huxley.wii.wiibox.mvp.user;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by huxley on 16/8/13.
 */
public class UserFragment extends BaseFragment implements UserContract.View {

    private UserContract.Presenter mUserPresenter;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void setPresenter(@NonNull UserContract.Presenter presenter) {
        mUserPresenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
    }

    private void initView() {

    }
}
