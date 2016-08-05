package com.huxley.wii.wiibox.mvp.main.gank.detail;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankModel;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

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
    private GankInfo mGankInfo;

    public GankDataDetailPresenter(@NonNull GankDataDetailContract.View gankDataDetailView, GankInfo gankInfo) {
        this.mGankDataDetailView = checkNotNull(gankDataDetailView);
        this.mGankDataDetailView.setPresenter(this);
        this.mGankInfo = checkNotNull(gankInfo);
    }

    @Override
    public void start() {
        mGankDataDetailView.showTitle(mGankInfo.date);

        if (mGankInfo.results != null) {
            mGankDataDetailView.showContent(GankModel.getInstance().getGankList(mGankInfo), true);
            return;
        }
        loadData();
    }

    @Override
    public void loadData() {
        GankModel.getInstance().getGankDetailInfo(mGankInfo.date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Object>>() {
                    @Override
                    public void onStart() {
                        mGankDataDetailView.showLoading();
                    }
                    @Override
                    public void onNext(List<Object> objects) {
                        mGankDataDetailView.showContent(objects, true);
                    }
                    @Override
                    public void onCompleted() {
                        mGankDataDetailView.dismissLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                        mGankDataDetailView.dismissLoading();
                        if (ExceptionHelper.isNoNetException(e) && !NetWorkHelper.isConnected()) {
                            mGankDataDetailView.showNotNet();
                        }else {
                            mGankDataDetailView.showError(e);
                        }
                    }
                });
    }
}
