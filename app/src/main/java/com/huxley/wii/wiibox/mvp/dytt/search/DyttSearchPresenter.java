package com.huxley.wii.wiibox.mvp.dytt.search;

import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;
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
public class DyttSearchPresenter implements DyttSearchContract.Presenter{

    private final DyttSearchContract.View ting56View;
    private boolean isFirst;
    private String nextUrl;
    private boolean noMore;

    public DyttSearchPresenter(DyttSearchContract.View tingPlayView) {
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

        DyttModel.getInstance().search(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DyttListBean>() {
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
                        if (ExceptionHelper.isNetException(e)) {
                            ting56View.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            ting56View.isEmptyView(isFirst);
                        }else {
                            ting56View.isErrorView(isFirst);
                        }
                    }
                    @Override
                    public void onNext(DyttListBean dyttListBean) {
                        if (dyttListBean == null || dyttListBean.isEmpty()) {
                            throw new EmptyException();
                        }
                        nextUrl = dyttListBean.nextUrl;
                        ting56View.setContent(isRefresh, isFirst, dyttListBean.mMovieInfos);
                        L.json(dyttListBean.toString());
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

        DyttModel.getInstance().search_1(DyttModel.SBaseUrl + nextUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DyttListBean>() {
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
                        if (ExceptionHelper.isNetException(e)) {
                            ting56View.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            ting56View.isEmptyView(isFirst);
                        }else {
                            ting56View.isErrorView(isFirst);
                        }
                    }
                    @Override
                    public void onNext(DyttListBean dyttListBean) {
                        if (dyttListBean == null || dyttListBean.isEmpty()) {
                            throw new EmptyException();
                        }
                        nextUrl = dyttListBean.nextUrl;
                        ting56View.setContent(false, isFirst, dyttListBean.mMovieInfos);
                        L.json(dyttListBean.toString());
                    }
                });
    }
}
