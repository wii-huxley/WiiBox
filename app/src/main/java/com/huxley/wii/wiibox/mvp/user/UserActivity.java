package com.huxley.wii.wiibox.mvp.user;

import android.content.Intent;
import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.mvp.user.model.UserModel;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

/**
 *
 * Created by huxley on 16/8/13.
 */
public class UserActivity extends BaseActivity {

    private UserFragment mUserFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        mUserFragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (mUserFragment == null) {
            mUserFragment = UserFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mUserFragment, R.id.fragmentContent);
        }
        new UserPresenter(mUserFragment, new UserModel(this));
    }

    /** 当 SSO 授权 Activity 退出时，该函数被调用 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        mUserFragment.weiboAuthorize(requestCode, resultCode, data);
    }
}