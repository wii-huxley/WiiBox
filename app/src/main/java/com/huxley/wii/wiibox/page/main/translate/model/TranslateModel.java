package com.huxley.wii.wiibox.page.main.translate.model;

import com.huxley.wii.wiibox.http.HttpClient;
import com.huxley.wii.wiibox.http.api.BaiduTranslateApi;
import com.huxley.wii.wiitools.common.Utils.CodeUtils;
import com.huxley.wii.wiitools.common.Utils.StringUtil;

import java.util.List;
import java.util.Random;

import rx.Observable;

/**
 *
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

    public Observable<String> baiduTranslate(String content){
        if (StringUtil.containsChinese(content)) { // 中译英
            return baiduTranslate(content, BaiduTranslateApi.ZH, BaiduTranslateApi.EN);
        } else { // 英译中
            return baiduTranslate(content, BaiduTranslateApi.EN, BaiduTranslateApi.ZH);
        }
    }

    /**
     * 百度翻译
     * @param content 原文
     * @param from 翻译源语言
     * @param to 译文语言
     * @return 译文
     */
    public Observable<String> baiduTranslate(String content, String from, String to) {
        int salt = new Random().nextInt(10000);
        String sign = CodeUtils.md5Encoder(BaiduTranslateApi.APP_ID + content + salt + BaiduTranslateApi.KEY, false);
        return HttpClient.getBaiduTranslateApi().translate(content, from, to, BaiduTranslateApi.APP_ID, salt, sign)
                .map(baiduTranslateInfo -> {
                    List<BaiduTranslateInfo.TransResultBean> trans_result = baiduTranslateInfo.trans_result;
                    StringBuilder builder = new StringBuilder();
                    if (trans_result != null && trans_result.size() > 0) {
                        for (BaiduTranslateInfo.TransResultBean tran : trans_result) {
                            builder.append(String.format("\t・\t%s\t:\t\n\t\t\t\t\t%s\n", tran.src, tran.dst));
                        }
                    }
                    return builder.toString();
                });
    }



    /**
     * 有道翻译
     */
    public Observable<String> youdaoTranslate(String content) {
        return HttpClient.getYoudaoTranslateApi().translate("WiiBox", "1704066412", "data", "json", "1.1", content)
                .map(youDaoTranslateInfo -> {
                    StringBuilder builder = new StringBuilder();
                    builder.append(String.format("\t・\t%s\t:\n", youDaoTranslateInfo.query));
                    List<String> translation = youDaoTranslateInfo.translation;
                    if (translation != null && translation.size() > 0) {
                        for (String content1 : translation) {
                            builder.append(String.format("\t\t\t\t%s\n", content1));
                        }
                    }
                    YouDaoTranslateInfo.BasicBean basic = youDaoTranslateInfo.basic;
                    if (basic != null) {
                        builder.append("\t・\t基本解释\t:\n");
                        if (basic.phonetic != null) {
                            builder.append(String.format("\t\t\t\t-\t拼音\t:\n\t\t\t\t\t\t\t%s\n", basic.phonetic));
                        }
                        List<String> explains = basic.explains;
                        if (explains != null && explains.size() > 0) {
                            builder.append("\t\t\t\t-\t翻译\t:\n");
                            for (String content1 : explains) {
                                builder.append(String.format("\t\t\t\t\t\t\t%s\n", content1));
                            }
                        }
                    }
                    List<YouDaoTranslateInfo.WebBean> web = youDaoTranslateInfo.web;
                    if (web != null && web.size() > 0) {
                        builder.append("\t・\t网络解释\t:\n");
                        for (YouDaoTranslateInfo.WebBean w : web) {
                            if (w != null) {
                                builder.append(String.format("\t\t\t\t-\t%s\t:\n", w.key));
                                List<String> value = w.value;
                                if (value != null && value.size() > 0) {
                                    for (String v : value) {
                                        builder.append(String.format("\t\t\t\t\t\t\t%s\n", v));
                                    }
                                }
                            }
                        }
                    }
                    return builder.toString();
                });
    }
}