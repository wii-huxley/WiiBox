package com.huxley.wii.wiibox.mvp.main.translate.model;

import com.huxley.wii.wiibox.http.HttpClient;
import com.huxley.wii.wiibox.http.api.BaiduTranslateApi;
import com.huxley.wii.wiitools.common.Utils.CodeUtils;

import java.util.Random;

import rx.Observable;

/**
 * Created by huxley on 16/7/19.
 */
public class TranslateModel {

    private static TranslateModel instance;

    private TranslateModel() {
    }

    public static TranslateModel getInstance() {
        if (instance == null) {
            synchronized (TranslateModel.class) {
                if (instance == null) {
                    instance = new TranslateModel();
                }
            }
        }
        return instance;
    }

    /**
     * 百度翻译 英译中
     */
    public Observable<BaiduTranslateInfo> baiduTranslateZh(String content) {
        return baiduTranslate(content, BaiduTranslateApi.EN, BaiduTranslateApi.ZH);
    }

    /**
     * 百度翻译 中译英
     */
    public Observable<BaiduTranslateInfo> baiduTranslateEn(String content) {
        return baiduTranslate(content, BaiduTranslateApi.ZH, BaiduTranslateApi.EN);
    }

    /**
     * 百度翻译
     * @param content 原文
     * @param from 翻译源语言
     * @param to 译文语言
     * @return 译文
     */
    public Observable<BaiduTranslateInfo> baiduTranslate(String content, String from, String to) {
        int salt = new Random().nextInt(10000);
        String sign = CodeUtils.md5Encoder(BaiduTranslateApi.APP_ID + content + salt + BaiduTranslateApi.KEY, false);
        return HttpClient.getBaiduTranslateApi().translate(content, from, to, BaiduTranslateApi.APP_ID, salt, sign);
    }



    /**
     * 有道翻译
     */
    public Observable<YouDaoTranslateInfo> youdaoTranslate(String content) {
        return HttpClient.getYoudaoTranslateApi().translate("WiiBox", "1704066412", "data", "json", "1.1", content);
    }
}