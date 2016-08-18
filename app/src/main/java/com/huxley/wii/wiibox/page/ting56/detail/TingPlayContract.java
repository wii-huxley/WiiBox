package com.huxley.wii.wiibox.page.ting56.detail;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
import com.huxley.wii.wiibox.page.ting56.model.Ting56Bean;
import com.huxley.wii.wiitools.base.net.INetView;

import java.util.List;

/**
 * Created by huxley on 16/7/13.
 */
public interface TingPlayContract {

    interface View extends BaseView<Presenter>, INetView<List<Ting56Bean.Ting56DetailBean>> {
    }

    interface Presenter extends BasePresenter {

        void reTry();

        void play(List<Ting56Bean.Ting56DetailBean> data, int position);

        void resume();

        void pause();

        String getTag();
    }
}
