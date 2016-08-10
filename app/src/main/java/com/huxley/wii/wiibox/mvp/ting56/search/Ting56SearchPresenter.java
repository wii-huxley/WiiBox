package com.huxley.wii.wiibox.mvp.ting56.search;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56ListBean;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Ting56SearchPresenter
 * Created by huxley on 16/7/13.
 */
public class Ting56SearchPresenter implements Ting56SearchContract.Presenter{

    private final Ting56SearchContract.View ting56View;
    private String nextUrl;

    public Ting56SearchPresenter(Ting56SearchContract.View tingPlayView) {
        this.ting56View = checkNotNull(tingPlayView);
        this.ting56View.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void search(String content) {
        Ting56Model.getInstance().search(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ting56ListBean>() {
                    @Override
                    public void onStart() {
                        ting56View.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        ting56View.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ting56View.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            ting56View.showNotNet();
                        } else {
                            ToastHelper.showInfo(R.string.str_error);
                        }
                    }

                    @Override
                    public void onNext(Ting56ListBean tingBookInfos) {
                        ting56View.showContent(tingBookInfos.ting56BeanList, true);
                        nextUrl = tingBookInfos.nextUrl;
                    }
                });
    }

    @Override
    public void reTry() {
        loadMore();
    }

    @Override
    public void loadMore() {
        if (nextUrl == null) {
            ToastHelper.showInfo(R.string.str_no_more);
            return;
        }
        Ting56Model.getInstance().search_1("http://www.ting56.com/search.asp" + nextUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ting56ListBean>() {
                    @Override
                    public void onStart() {
                        ting56View.showLoading();
                    }
                    @Override
                    public void onCompleted() {
                        ting56View.dismissLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                        ting56View.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            ting56View.showNotNet();
                        } else {
                            ting56View.showError(e);
                        }
                    }
                    @Override
                    public void onNext(Ting56ListBean tingBookInfos) {
                        ting56View.showContent(tingBookInfos.ting56BeanList, false);
                        nextUrl = tingBookInfos.nextUrl;
                    }
                });
    }
}
