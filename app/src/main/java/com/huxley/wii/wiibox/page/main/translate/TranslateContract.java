package com.huxley.wii.wiibox.page.main.translate;

import com.huxley.wii.wiibox.page.BasePresenter;
import com.huxley.wii.wiibox.page.BaseView;
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
