package com.huxley.wii.wiibox.mvp.ting56;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56ListBean;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.common.Utils.L;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import rx.Subscriber;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 *
 * Created by huxley on 16/7/12.
 */
public class Ting56Presenter implements Ting56Contract.Presenter{

    private Ting56Contract.View ting56View;
    private String mUrl;
    private String nextUrl;
    private boolean errorState;

    public Ting56Presenter(Ting56Contract.View ting56View, String url) {
        this.ting56View = checkNotNull(ting56View);
        ting56View.setPresenter(this);
        this.mUrl = url;
    }

    @Override
    public void start() {
        refresh();
    }


    @Override
    public void refresh() {
        loadTing56List(Ting56Model.URL_BASE + mUrl, true);
    }

    @Override
    public void loadMore() {
        if (nextUrl == null) {
            ToastHelper.showInfo(R.string.str_no_more);
            return;
        }
        loadTing56List(Ting56Model.URL_BASE + nextUrl, false);
    }

    @Override
    public void reTry() {
        if (errorState) {
            refresh();
        } else {
            loadMore();
        }
    }

    public void loadTing56List(String content, boolean isRefresh) {
        Ting56Model.getInstance().getList(content)
                .subscribe(new Subscriber<Ting56ListBean>() {
                    @Override
                    public void onStart() {
                        ting56View.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        ting56View.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ting56View.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            ting56View.showNotNet();
                        } else {
                            errorState = isRefresh;
                            ting56View.showError(e);
                        }
                    }

                    @Override
                    public void onNext(Ting56ListBean tingBookInfos) {
                        ting56View.showContent(tingBookInfos.ting56BeanList, isRefresh);
                        if (nextUrl == null ? mUrl.equals(tingBookInfos.nextUrl) : nextUrl.equals(tingBookInfos.nextUrl)) {
                            nextUrl = null;
                        } else {
                            nextUrl = tingBookInfos.nextUrl;
                        }
                    }
                });
    }
}
