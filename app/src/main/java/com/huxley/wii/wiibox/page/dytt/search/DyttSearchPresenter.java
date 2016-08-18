package com.huxley.wii.wiibox.page.dytt.search;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.page.dytt.model.DyttListBean;
import com.huxley.wii.wiibox.page.dytt.model.DyttModel;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import rx.Subscriber;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by huxley on 16/7/13.
 */
public class DyttSearchPresenter implements DyttSearchContract.Presenter{

    private final DyttSearchContract.View mView;
    private       String                  nextUrl;

    public DyttSearchPresenter(DyttSearchContract.View view) {
        this.mView = checkNotNull(view);
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void search(String content) {
        DyttModel.getInstance().search(content)
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
                            ToastHelper.showInfo(R.string.str_prompt_loading_fail);
                        }
                    }

                    @Override
                    public void onNext(DyttListBean dyttListBean) {
                        nextUrl = dyttListBean.nextUrl;
                        mView.showContent(dyttListBean.mMovieInfos, true);
                    }
                });
    }

    @Override
    public void loadMore() {
        if (nextUrl == null) {
            ToastHelper.showInfo(R.string.str_prompt_no_more);
            return;
        }
        DyttModel.getInstance().search_1(DyttModel.SBaseUrl + nextUrl)
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
                        }
                    }
                    @Override
                    public void onNext(DyttListBean dyttListBean) {
                        nextUrl = dyttListBean.nextUrl;
                        mView.showContent(dyttListBean.mMovieInfos, false);
                    }
                });
    }

    @Override
    public void reTry() {
        loadMore();
    }
}
