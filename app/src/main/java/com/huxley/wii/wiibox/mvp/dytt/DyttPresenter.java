package com.huxley.wii.wiibox.mvp.dytt;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;
import com.huxley.wii.wiitools.common.Utils.NonNull;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * DyttPresenter
 * Created by huxley on 16/7/21.
 */
public class DyttPresenter implements DyttContract.Presenter {

    private DyttContract.View mView;
    private String mUrl;
    private String nextUrl;
    private boolean errorState;

    public DyttPresenter(DyttContract.View view, String url) {
        this.mView = NonNull.checkNotNull(view);
        this.mUrl = NonNull.checkNotNull(url);
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        List<DyttListBean.MovieInfo> homeData = DyttModel.getInstance().getHomeData(mUrl);
        if (homeData != null) {
            mView.showContent(homeData, false);
            return;
        }
        loadMovie(mUrl, true);
    }

    @Override
    public void refresh(){
        loadMovie(mUrl, true);
    }

    @Override
    public void loadMore() {
        if (nextUrl == null) {
            ToastHelper.showInfo(R.string.str_no_more);
            return;
        }
        loadMovie(mUrl + nextUrl, false);
    }

    @Override
    public void reTry() {
        if (errorState) {
            refresh();
        } else {
            loadMore();
        }
    }

    private void loadMovie(String url,final boolean isRefresh){
        DyttModel.getInstance().getMovieList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DyttListBean>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            mView.showNotNet();
                        } else {
                            mView.showError(e);
                            errorState = isRefresh;
                        }
                    }

                    @Override
                    public void onNext(DyttListBean movieListInfo) {
                        List<DyttListBean.MovieInfo> mMovieInfos = movieListInfo.mMovieInfos;
                        if (isRefresh) {
                            DyttModel.getInstance().clearHomeData(mUrl);
                        }
                        DyttModel.getInstance().setHomeData(mUrl, mMovieInfos);
                        mView.showContent(mMovieInfos, isRefresh);
                        DyttPresenter.this.nextUrl = movieListInfo.nextUrl;
                    }
                });
    }
}
