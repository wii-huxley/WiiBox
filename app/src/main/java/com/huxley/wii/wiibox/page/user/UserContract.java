package com.huxley.wii.wiibox.page.user;

import android.content.Intent;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiitools.base.net.INetView;

/**
 *
 * Created by huxley on 16/8/13.
 */
public interface UserContract {

    interface View extends BaseView<Presenter>, INetView<Object> {

        void isWeiboSessionValid();
    }

    interface Presenter extends BasePresenter {
        void weiboAuthorize();

        void weiboAuthorize(int requestCode, int resultCode, Intent data);

        void weixinAuthorize();

        void qqAuthorize();
    }
}