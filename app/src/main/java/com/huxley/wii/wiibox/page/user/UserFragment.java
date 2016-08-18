package com.huxley.wii.wiibox.page.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by huxley on 16/8/13.
 */
public class UserFragment extends BaseFragment implements UserContract.View {

    private UserContract.Presenter mUserPresenter;
    private Button btnWeibo;
    private Button btnWeixin;
    private Button btnQQ;

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
        initListener();
        mUserPresenter.start();
    }

    private void initView() {
        btnWeibo = $(R.id.btnWeibo);
        btnWeixin = $(R.id.btnWeixin);
        btnQQ = $(R.id.btnQQ);

        IWeiboShareAPI weiboAPI = WeiboShareSDK.createWeiboAPI(getActivity(), "");
        weiboAPI.registerApp();
    }

    private void initListener() {
        btnWeibo.setOnClickListener(v -> mUserPresenter.weiboAuthorize());
        btnWeixin.setOnClickListener(v -> mUserPresenter.weixinAuthorize());
        btnQQ.setOnClickListener(v -> mUserPresenter.qqAuthorize());
    }

    @Override
    public void isWeiboSessionValid() {
        btnWeibo.setText(R.string.user_is_auth);
        btnWeibo.setEnabled(false);
    }

    @Override
    public void showLoading() {
        isLoading(true);
    }

    @Override
    public void dismissLoading() {
        isLoading(false);
    }

    @Override
    public void showNotNet() {
        SnackbarHelper.showNoNetInfo(rootView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showInfo(rootView, R.string.str_prompt_loading_fail);
    }

    @Override
    public void showContent(Object data, boolean isRefresh) {

    }

    public void weiboAuthorize(int requestCode, int resultCode, Intent data) {
        mUserPresenter.weiboAuthorize(requestCode, resultCode, data);
    }
}
