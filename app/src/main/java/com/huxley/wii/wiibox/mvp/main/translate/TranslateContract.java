package com.huxley.wii.wiibox.mvp.main.translate;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiitools.base.net.INetView;

/**
 * Created by huxley on 16/7/18.
 */
public interface TranslateContract {
    interface View extends BaseView<Presenter>, INetView<String> {

    }
    interface Presenter extends BasePresenter{

        void baiduTranslate(String content);

        void youdaoTranslate(String content);
    }
}
