package com.huxley.wii.wiibox.mvp.main.gank;

import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankModel;
import com.huxley.wii.wiitools.common.Utils.GsonUtils;
import com.huxley.wii.wiitools.common.Utils.L;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/5.
 */
public class GankPresenter implements GankContract.Presenter {

    private GankContract.View mGankView;

    public GankPresenter(GankContract.View gankView) {
        mGankView = checkNotNull(gankView);
        mGankView.setPresenter(this);
    }

    @Override
    public void start() {
        getLocalData();
    }

    public void getLocalData() {
        GankModel.getInstance().getLocalGankDatas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GankInfo>>() {
                    @Override
                    public void onNext(List<GankInfo> gankInfos) {
                        L.json(GsonUtils.get().toJson(gankInfos));
                        mGankView.showContent(gankInfos, true);
                    }
                    @Override
                    public void onCompleted() {
                        loadGank(true);
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (ExceptionHelper.isNullPointerException(e)) {
                            loadGank(true);
                        } else {
                            mGankView.showError(e);
                        }
                    }
                });
    }

    @Override
    public void loadGank(boolean isRrefresh) {
        GankModel.getInstance().getGankInfos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GankInfo>>() {
                    @Override
                    public void onStart() {
                        mGankView.showLoading();
                    }
                    @Override
                    public void onNext(List<GankInfo> gankInfos) {
                        mGankView.showContent(gankInfos, isRrefresh);
                    }
                    @Override
                    public void onCompleted() {
                        mGankView.dismissLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                        mGankView.dismissLoading();
                        if (ExceptionHelper.isNoNetException(e) && !NetWorkHelper.isConnected()) {
                            mGankView.showNotNet();
                        } else {
                            mGankView.showError(e);
                        }
                    }
                });
    }
}
