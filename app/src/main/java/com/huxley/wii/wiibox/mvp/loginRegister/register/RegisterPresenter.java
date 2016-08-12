package com.huxley.wii.wiibox.mvp.loginRegister.register;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.loginRegister.model.RegisterModel;
import com.huxley.wii.wiibox.mvp.loginRegister.model.UserInfo;
import com.huxley.wii.wiitools.common.Utils.L;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by LeiJin01 on 2016/8/11.
 */
public class RegisterPresenter implements RegisterContact.Presenter{

    private RegisterContact.View mView;
    private RegisterModel mModel;

    public RegisterPresenter(@NonNull RegisterContact.View view, RegisterModel model) {
        mView = checkNotNull(view);
        mModel = checkNotNull(model);
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void register(String userName, String password) {
        mView.showLoading();
        mModel.register(userName, password, new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                mView.dismissLoading();
                if (e == null) {
                    mView.showContent(userInfo, true);
                } else if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                    mView.showNotNet();
                } else {
                    mView.showError(e);
                }
            }
        });
    }

    @Override
    public void requestSMSCode(String phoneNum) {
        mModel.requestSMSCode(phoneNum, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    L.i("发送成功 -- integer = " + integer);
                } else {
                    L.i("发送失败 -- integer = " + integer);
                }
            }
        });
    }

    @Override
    public void signOrLoginByMobilePhone(String phoneNum, String code) {
        L.i("phoneNum = " + phoneNum + ", code = " + code);
        mView.showLoading();
        mModel.signOrLoginByMobilePhone(phoneNum, code, new LogInListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                mView.dismissLoading();
                if (e == null || e.getErrorCode() == 9015) {
                    mView.showContent(userInfo, true);
                } else if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                    mView.showNotNet();
                } else {
                    mView.showError(e);
                    L.e(e);
                }
            }
        });
    }

}
