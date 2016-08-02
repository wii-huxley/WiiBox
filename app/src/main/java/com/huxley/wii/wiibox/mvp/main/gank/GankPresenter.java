package com.huxley.wii.wiibox.mvp.main.gank;

import com.huxley.wii.wiitools.exception.EmptyException;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/5.
 */
public class GankPresenter implements GankContract.Presenter {

    private GankContract.View mGankView;

    private boolean mFirstLoad = true;

    public GankPresenter(GankContract.View gankView) {
        mGankView = checkNotNull(gankView);
        mGankView.setPresenter(this);
    }

    @Override
    public void start() {
        mFirstLoad = true;
        loadGank(false);
    }

    @Override
    public void loadGank(boolean isRrefresh) {
        if (mFirstLoad) {
            mGankView.setProgress(true);
        }
        GankModel.getInstance().getGankInfos(isRrefresh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GankInfo>>() {
                    @Override
                    public void onCompleted() {
                        if (mFirstLoad) {
                            mFirstLoad = false;
                            mGankView.setProgress(false);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (mFirstLoad) {
                            mGankView.setProgress(false);
                        }
                        if (ExceptionHelper.isNoNetException(e)) {
                            mGankView.isNoNetView();
                        } else if (ExceptionHelper.isEmptyException(e)) {
                            mGankView.isEmptyView();
                        }else {
                            mGankView.isErrorView();
                        }
                    }
                    @Override
                    public void onNext(List<GankInfo> gankInfos) {
                        if (mFirstLoad && (gankInfos == null || gankInfos.size() <= 0)) {
                            throw new EmptyException();
                        }
                        mGankView.isContentView(gankInfos, mFirstLoad);
                    }
                });
    }
}
