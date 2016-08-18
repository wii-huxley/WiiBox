package com.huxley.wii.wiibox.page.codekk;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.page.codekk.model.CodekkHomeListBean;
import com.huxley.wii.wiibox.page.codekk.model.CodekkModel;
import com.huxley.wii.wiibox.page.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiibox.page.codekk.model.ResultBean;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import java.util.List;

import rx.Subscriber;

/**
 * CodekkPresenter
 * Created by huxley on 16/7/31.
 */

public class CodekkPresenter implements CodekkContract.Presenter {

    private CodekkContract.View mView;
    // 首页的下一页页数
    private int                 nextPage;
    private boolean             hasMore;
    private boolean             errorState;

    public CodekkPresenter(CodekkContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        nextPage = 1;
        hasMore = true;
        mView.showContent(CodekkModel.getInstance().getCodekkHomeData(), false);
        loadCodekkList(nextPage, true);
    }

    @Override
    public void loadMore() {
        loadCodekkList(nextPage, false);
    }

    @Override
    public void refresh() {
        nextPage = 1;
        hasMore = true;
        loadCodekkList(nextPage, true);
    }

    private void loadCodekkList(int page, boolean isRefresh) {
        CodekkModel.getInstance().getCodekkList(page)
                .subscribe(new Subscriber<ResultBean<CodekkHomeListBean>>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissLoading();
                        if (!isRefresh) {
                            CodekkPresenter.this.nextPage++;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            mView.showNotNet();
                        } else {
                            errorState = isRefresh;
                            mView.showError(e);
                        }
                    }

                    @Override
                    public void onNext(ResultBean<CodekkHomeListBean> datas) {
                        if (datas.code != 0) {
                            throw new RuntimeException("code 不为 0");
                        }
                        List<CodekkProjectBean> projectArray = datas.data.projectArray;
                        if (projectArray.size() <= 0) {
                            hasMore = false;
                            ToastHelper.showInfo(R.string.str_prompt_no_more);
                            return;
                        }
                        if (isRefresh) {
                            CodekkModel.getInstance().removeAllData();
                        }
                        CodekkModel.getInstance().setCodekkHomeData(projectArray);
                        mView.showContent(projectArray, isRefresh);
                    }
                });
    }

    @Override
    public void reTry() {
        loadCodekkList(nextPage, errorState);
    }

    @Override
    public boolean hasMore() {
        return hasMore;
    }
}
