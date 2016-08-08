package com.huxley.wii.wiibox.mvp.codekk;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkHomeListBean;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkModel;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkSearchListBean;
import com.huxley.wii.wiibox.mvp.codekk.model.ResultBean;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;
import com.thefinestartist.utils.log.L;

import java.util.List;

import rx.Subscriber;

/**
 * Created by huxley on 16/7/31.
 */

public class CodekkPresenter implements CodekkContract.Presenter {

    private CodekkContract.View mView;
    // 首页的下一页页数
    private int                 homeNextPage;
    // 搜索的下一页页数
    private int                 searchNextPage;
    private boolean             hasMore;
    private String searchContent = null;

    public CodekkPresenter(CodekkContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        homeNextPage = 1;
        hasMore = true;
        searchContent = null;
        mView.showContent(CodekkModel.getInstance().getCodekkHomeData(), false);
        loadCodekkList(homeNextPage, true);
    }

    @Override
    public void loadMore() {
        if (searchContent != null) {
            search(searchContent, false);
        } else {
            loadCodekkList(homeNextPage, false);
        }
    }

    @Override
    public void refresh() {
        homeNextPage = 1;
        hasMore = true;
        searchContent = null;
        loadCodekkList(homeNextPage, true);
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
                            CodekkPresenter.this.homeNextPage++;
                        }
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
                    public void onNext(ResultBean<CodekkHomeListBean> datas) {
                        if (datas.code != 0) {
                            throw new RuntimeException("code 不为 0");
                        }
                        List<CodekkProjectBean> projectArray = datas.data.projectArray;
                        if (projectArray.size() <= 0) {
                            hasMore = false;
                            ToastHelper.showInfo(R.string.str_no_more);
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
    public void search(String query, boolean isFirst) {
        if (isFirst) {
            searchNextPage = 1;
            searchContent = query;
            hasMore = true;
        }
        CodekkModel.getInstance().getSearchList(query, searchNextPage)
                .subscribe(new Subscriber<ResultBean<CodekkSearchListBean>>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        CodekkPresenter.this.searchNextPage++;
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
                    public void onNext(ResultBean<CodekkSearchListBean> datas) {
                        if (datas.code != 0) {
                            throw new RuntimeException("code 不为 0");
                        }
                        List<CodekkProjectBean> projectArray = datas.data.projectArray;
                        if (projectArray.size() <= 0) {
                            hasMore = false;
                            ToastHelper.showInfo(isFirst ? R.string.str_search_empty : R.string.str_no_more);
                            return;
                        }
                        mView.showContent(projectArray, isFirst);
                        L.json(datas.toString());
                    }
                });
    }

    @Override
    public void resetContent() {
        searchContent = null;
        mView.showContent(CodekkModel.getInstance().getCodekkHomeData(), false);
    }

    @Override
    public boolean hasMore() {
        return hasMore;
    }
}
