package com.huxley.wii.wiibox.mvp.main.gank.detail;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.main.gank.GankModel;
import com.huxley.wii.wiitools.exception.EmptyException;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/15.
 */
public class GankDataDetailPresenter implements GankDataDetailContract.Presenter {

    private final GankDataDetailContract.View mGankDataDetailView;

    public GankDataDetailPresenter(@NonNull GankDataDetailContract.View gankDataDetailView) {
        this.mGankDataDetailView = checkNotNull(gankDataDetailView);
        this.mGankDataDetailView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(String date) {

        GankModel.getInstance().getGankDetailInfo(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Object>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (ExceptionHelper.isNoNetException(e)) {
                            mGankDataDetailView.isNoNetView();
                        } else if (ExceptionHelper.isEmptyException(e)) {
                            mGankDataDetailView.isEmptyView();
                        }else {
                            mGankDataDetailView.isErrorView();
                        }
                    }

                    @Override
                    public void onNext(List<Object> objects) {
                        if (objects == null || objects.size() <= 0) {
                            throw new EmptyException();
                        }
                        mGankDataDetailView.isContentView(objects);
                    }
                });
    }
}
