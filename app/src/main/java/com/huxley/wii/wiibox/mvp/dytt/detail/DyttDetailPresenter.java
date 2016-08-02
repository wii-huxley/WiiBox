package com.huxley.wii.wiibox.mvp.dytt.detail;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttDetailInfo;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DyttDetailPresenter implements DyttDetailContract.Presenter {

    private final DyttDetailContract.View mDyttDetailView;

    public DyttDetailPresenter(@NonNull DyttDetailContract.View view) {
        mDyttDetailView = view;
        mDyttDetailView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadData(String url) {
        DyttModel.getInstance().getMovieDetailInfo(url)
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
                        mDyttDetailView.setContent(movieDetailInfo);
                    }
                });
    }
}
