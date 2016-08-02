package com.huxley.wii.wiibox.mvp.codekk.model;

import com.huxley.wii.wiibox.http.HttpClient;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 16/7/31.
 */
public class CodekkModel {

    private static CodekkModel instance;

    private CodekkModel() {
    }

    public static CodekkModel getInstance() {
        if (instance == null) {
            synchronized (CodekkModel.class) {
                if (instance == null) {
                    instance = new CodekkModel();
                }
            }
        }
        return instance;
    }

    public Observable<ResultBean<CodekkHomeListBean>> getCodekkList(int page) {
        return HttpClient.getCodekkApi().getHomeList(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}