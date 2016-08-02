package com.huxley.wii.wiibox.mvp.main.translate;

import com.huxley.wii.wiibox.mvp.BasePresenter;
import com.huxley.wii.wiibox.mvp.BaseView;
import com.huxley.wii.wiibox.mvp.main.translate.model.BaiduTranslateInfo;
import com.huxley.wii.wiibox.mvp.main.translate.model.YouDaoTranslateInfo;

/**
 * Created by huxley on 16/7/18.
 */
public interface TranslateContract {
    interface View extends BaseView<Presenter>{

        void setYoudaoTranslateContent(YouDaoTranslateInfo youDaoTranslateInfo);

        void setBaiduTranslateContent(BaiduTranslateInfo baiduTranslateInfo);
    }
    interface Presenter extends BasePresenter{

        void baiduTranslate(String content);

        void youdaoTranslate(String content);
    }
}
