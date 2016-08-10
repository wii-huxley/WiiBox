package com.huxley.wii.wiibox.mvp.ting56;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 *
 * Created by huxley on 16/7/5.
 */
public interface Ting56Contract {

    interface View extends BaseView<Presenter>, INetView<List<Ting56Bean>> {
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void loadMore();

        void reTry();
    }
}
