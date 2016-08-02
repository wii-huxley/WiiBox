package com.huxley.wii.wiibox.mvp.ting56;

import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56ListBean;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.exception.EmptyException;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by huxley on 16/7/12.
 */
public class Ting56Presenter implements Ting56Contract.Presenter{

    private Ting56Contract.View ting56View;
    private String mUrl;
    private boolean isFirst;
    private String nextUrl;

    public Ting56Presenter(Ting56Contract.View ting56View, String url) {
        this.ting56View = checkNotNull(ting56View);
        ting56View.setPresenter(this);
        this.mUrl = url;
    }

    @Override
    public void start() {
        isFirst = true;
        loadTing56List(false, mUrl);
    }


    @Override
    public void refresh() {
        loadTing56List(true, mUrl);
    }

    @Override
    public void loadMore() {
        if (nextUrl == null) {
            ToastHelper.showInfo("没有更多...");
            return;
        }
        loadTing56List(false, Ting56Model.URL_BASE + nextUrl);
    }

    public void loadTing56List(final boolean isRefresh, String content) {
        if (isFirst) {
            ting56View.setProgress(true);
        }
        Ting56Model.getInstance().getList(content)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ting56ListBean>() {
                    @Override
                    public void onCompleted() {
                        if (isFirst) {
                            ting56View.setProgress(false);
                            isFirst = false;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (isFirst) {
                            ting56View.setProgress(false);
                        }
                        if (ExceptionHelper.isNoNetException(e)) {
                            ting56View.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            ting56View.isEmptyView(isFirst);
                        }else {
                            ting56View.isErrorView(isFirst);
                        }
                    }
                    @Override
                    public void onNext(Ting56ListBean tingBookInfos) {
                        if (tingBookInfos == null || tingBookInfos.isEmp()) {
                            throw new EmptyException();
                        }
                        nextUrl = tingBookInfos.nextUrl;
                        ting56View.setContent(tingBookInfos.ting56BeanList, isRefresh, isFirst);
                    }
                });
    }
}
