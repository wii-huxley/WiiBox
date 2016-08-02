package com.huxley.wii.wiibox.mvp.dytt;

import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;
import com.huxley.wii.wiitools.exception.EmptyException;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 16/7/21.
 */
public class DyttPresenter implements DyttContract.Presenter {

    private DyttContract.View mView;
    private String mUrl;
    private boolean isFirst;
    private String nextUrl;
    private boolean noMore;

    public DyttPresenter(DyttContract.View view, String url) {
        this.mView = view;
        this.mUrl = url;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        isFirst = true;
        loadMovie(false, mUrl + DyttModel.FirstUrl);
    }

    private void loadMovie(final boolean isRefresh, String url){
        if (isFirst) {
            mView.setProgress(true);
        }
        DyttModel.getInstance().getMovieList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DyttListBean>() {
                    @Override
                    public void onCompleted() {
                        if (isFirst) {
                            mView.setProgress(false);
                            isFirst = false;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (isFirst) {
                            mView.setProgress(false);
                        }
                        if (ExceptionHelper.isNoNetException(e)) {
                            mView.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            mView.isEmptyView(isFirst);
                        }else {
                            mView.isErrorView(isFirst);
                        }
                    }
                    @Override
                    public void onNext(DyttListBean movieListInfo) {
                        if (isFirst && (movieListInfo == null || movieListInfo.isEmpty())) {
                            throw new EmptyException();
                        }
                        DyttPresenter.this.nextUrl = movieListInfo.nextUrl;
                        mView.setContent(movieListInfo.mMovieInfos, isRefresh, isFirst);
                    }
                });
    }

    @Override
    public void refresh(){
        loadMovie(true, mUrl);
    }

    @Override
    public void loadMore() {
        if (nextUrl == null && !noMore) {
            noMore = true;
            ToastHelper.showInfo("没有更多...");
            return;
        }
        loadMovie(false, mUrl + nextUrl);
    }
}
