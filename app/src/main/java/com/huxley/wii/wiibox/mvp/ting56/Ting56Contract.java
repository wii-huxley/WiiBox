package com.huxley.wii.wiibox.mvp.ting56;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;

import java.util.List;

/**
 * Created by huxley on 16/7/5.
 */
public interface Ting56Contract {

    interface View extends BaseView<Presenter> {

        void isNoNetView(boolean isFirst);

        void isEmptyView(boolean isFirst);

        void isErrorView(boolean isFirst);

        void setProgress(boolean isShow);

        void setContent(List<Ting56Bean> ting56BeanList, boolean isRefresh, boolean isFirst);
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void loadMore();
    }
}
