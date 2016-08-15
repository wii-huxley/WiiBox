package com.huxley.wii.wiibox.mvp.user;

import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

/**
 *
 * Created by huxley on 16/8/13.
 */
public class UserActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        UserFragment mUserFragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (mUserFragment == null) {
            mUserFragment = UserFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mUserFragment, R.id.fragmentContent);
        }
        new UserPresenter(mUserFragment);
    }
}
