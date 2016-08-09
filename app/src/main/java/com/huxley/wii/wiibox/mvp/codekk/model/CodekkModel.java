package com.huxley.wii.wiibox.mvp.codekk.model;

import com.huxley.wii.wiibox.http.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 16/7/31.
 */
public class CodekkModel {

    private List<CodekkProjectBean> codeHomeData;

    private static CodekkModel instance;

    private CodekkModel() {
        codeHomeData = new ArrayList<>();
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

    public Observable<ResultBean<CodekkSearchListBean>> getSearchList(String content, int page){
        return Observable.just(content)
                .subscribeOn(Schedulers.io())
                .debounce(1000, TimeUnit.MILLISECONDS)
                .switchMap(s -> HttpClient.getCodekkApi().getSearchList(s, page))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public List<CodekkProjectBean> getCodekkHomeData() {
        return this.codeHomeData;
    }

    public void removeAllData() {
        this.codeHomeData.clear();
    }

    public void setCodekkHomeData(List<CodekkProjectBean> codeHomeData) {
        this.codeHomeData.addAll(codeHomeData);
    }
}