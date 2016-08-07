package com.huxley.wii.wiibox.mvp.tieba;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;

/**
 * Created by huxley on 16/8/7.
 */
public interface TiebaContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void getBarData(String barName);
    }
}