package com.huxley.wii.wiibox.mvp.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ResHelper;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.utils.SP;
import com.huxley.wii.wiibox.mvp.user.model.UserModel;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by huxley on 16/8/13.
 */
public class UserPresenter implements UserContract.Presenter {

    private final UserContract.View mUserView;
    private UserModel mUserModel;

    public UserPresenter(@NonNull UserContract.View view, @NonNull UserModel model) {
        mUserView = checkNotNull(view);
        mUserModel = checkNotNull(model);
        mUserView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mUserModel.isWeiboSessionValid()) {
            mUserView.isWeiboSessionValid();
        }

    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在中调用后，该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    @Override
    public void weiboAuthorize() {
        mUserModel.weiboAuthorize(new WeiboAuthListener() {
            @Override
            public void onComplete(Bundle bundle) {
                Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(bundle);
                //从这里获取用户输入的 电话号码信息
                //String  phoneNum =  token.getPhoneNum();
                if (token.isSessionValid()) {
                    mUserView.isWeiboSessionValid();
                    SP.User.saveWeiboAccessToken(token);
                } else {
                    // 以下几种情况，您会收到 Code：
                    // 1. 当您未在平台上注册的应用程序的包名与签名时；
                    // 2. 当您注册的应用程序包名与签名不正确时；
                    // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                    String code = bundle.getString("code");
                    String message = ResHelper.getString(R.string.user_auth_failed);
                    if (!TextUtils.isEmpty(code)) {
                        message = message + "\nObtained the code: " + code;
                    }
                    ToastHelper.showInfo(message);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                ToastHelper.showInfo("授权失败 : " + e.getMessage());
            }

            @Override
            public void onCancel() {
                ToastHelper.showInfo(R.string.user_auth_canceled);
            }
        });
    }

    @Override
    public void weiboAuthorize(int requestCode, int resultCode, Intent data) {
        mUserModel.weiboAuthorize(requestCode, resultCode, data);
    }

    @Override
    public void weixinAuthorize() {

    }

    @Override
    public void qqAuthorize() {

    }
}