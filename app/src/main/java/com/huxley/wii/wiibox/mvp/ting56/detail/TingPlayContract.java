package com.huxley.wii.wiibox.mvp.ting56.detail;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;

import java.util.List;

/**
 * Created by huxley on 16/7/13.
 */
public interface TingPlayContract {

    interface View extends BaseView<Presenter> {

        void isEmptyView();

        void isErrorView();

        void isNoNetView();

        void setProgress(boolean isShow);

        void setContent(List<Ting56Bean.Ting56DetailBean> ting56DetailBeen);
    }

    interface Presenter extends BasePresenter {
        void openBook(String content);
    }
}
