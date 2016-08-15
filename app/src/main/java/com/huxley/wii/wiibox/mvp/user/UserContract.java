package com.huxley.wii.wiibox.mvp.user;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;

/**
 * Created by huxley on 16/8/13.
 */
public interface UserContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}