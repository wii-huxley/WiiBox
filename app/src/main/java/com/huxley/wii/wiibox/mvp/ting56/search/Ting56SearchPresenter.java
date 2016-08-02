package com.huxley.wii.wiibox.mvp.ting56.search;

import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56ListBean;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.exception.EmptyException;
import com.thefinestartist.utils.log.L;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/13.
 */
public class Ting56SearchPresenter implements Ting56SearchContract.Presenter{

    private final Ting56SearchContract.View ting56View;
    private boolean isFirst;
    private String nextUrl;
    private boolean noMore;

    public Ting56SearchPresenter(Ting56SearchContract.View tingPlayView) {
        this.ting56View = checkNotNull(tingPlayView);
        this.ting56View.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void search(String content) {
        isFirst = true;
        search(true, content);
    }

    private void search(final boolean isRefresh, String content){
        if (isFirst) {
            ting56View.setProgress(true);
        }

        Ting56Model.getInstance().search(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ting56ListBean>() {
                    @Override
                    public void onCompleted() {
                        L.i("completed");
                        if (isFirst) {
                            ting56View.setProgress(false);
                            isFirst = false;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        L.i(e);
                        if (isFirst) {
                            ting56View.setProgress(false);
                        }
                        if (ExceptionHelper.isNoNetException(e)) {
                            ting56View.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            ting56View.isEmptyView(isFirst);
                        }else {
                            ting56View.isErrorView(isFirst);
                        }
                    }
                    @Override
                    public void onNext(Ting56ListBean tingBookInfos) {
                        if (tingBookInfos == null || tingBookInfos.isEmp()) {
                            throw new EmptyException();
                        }
                        nextUrl = tingBookInfos.nextUrl;
                        ting56View.setContent(isRefresh, isFirst, tingBookInfos.ting56BeanList);
                        L.json(tingBookInfos.toString());
                    }
                });
    }

    @Override
    public void loadMore() {
        if (nextUrl == null && !noMore) {
            noMore = true;
            ToastHelper.showInfo("没有更多...");
            return;
        }

        Ting56Model.getInstance().search_1("http://www.ting56.com/search.asp" + nextUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ting56ListBean>() {
                    @Override
                    public void onCompleted() {
                        L.i("completed");
                        if (isFirst) {
                            ting56View.setProgress(false);
                            isFirst = false;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        L.i(e);
                        if (isFirst) {
                            ting56View.setProgress(false);
                        }
                        if (ExceptionHelper.isNoNetException(e)) {
                            ting56View.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            ting56View.isEmptyView(isFirst);
                        }else {
                            ting56View.isErrorView(isFirst);
                        }
                    }
                    @Override
                    public void onNext(Ting56ListBean tingBookInfos) {
                        if (tingBookInfos == null || tingBookInfos.isEmp()) {
                            throw new EmptyException();
                        }
                        nextUrl = tingBookInfos.nextUrl;
                        ting56View.setContent(false, isFirst, tingBookInfos.ting56BeanList);
                        L.json(tingBookInfos.toString());
                    }
                });
    }
}
