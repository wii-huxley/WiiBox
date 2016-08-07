package com.huxley.wii.wiibox.mvp.tieba.model;

import com.baidu.tiebasdk.TiebaSDK;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 16/8/7.
 */
public class TiebaModel {

    private static TiebaModel instance;

    private TiebaModel() {
    }

    public static TiebaModel getInstance() {
        if (instance == null) {
            synchronized (TiebaModel.class) {
                if (instance == null) {
                    instance = new TiebaModel();
                }
            }
        }
        return instance;
    }

    public Observable<String> getBarData(String barName) {
        return Observable.just(barName)
                .subscribeOn(Schedulers.io())
                .map(TiebaSDK::getBarData)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void openBar() {

    }
}