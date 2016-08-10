package com.huxley.wii.wiibox.mvp.dytt.detail;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.dytt.model.DyttDetailInfo;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class DyttDetailPresenter implements DyttDetailContract.Presenter {

    private final DyttDetailContract.View mDyttDetailView;
    private DyttListBean.MovieInfo mMovieInfo;

    public DyttDetailPresenter(@NonNull DyttDetailContract.View view, @NonNull DyttListBean.MovieInfo movieInfo) {
        this.mDyttDetailView = checkNotNull(view);
        this.mMovieInfo = checkNotNull(movieInfo);
        mDyttDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        mDyttDetailView.setTitle(mMovieInfo.name);
        DyttModel.getInstance().getMovieDetailInfo(mMovieInfo.url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DyttDetailInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DyttDetailInfo movieDetailInfo) {
                        mDyttDetailView.showContent(movieDetailInfo, true);
                    }
                });
    }
}
