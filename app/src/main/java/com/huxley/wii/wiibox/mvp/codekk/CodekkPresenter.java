package com.huxley.wii.wiibox.mvp.codekk;

import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkHomeListBean;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkModel;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkSearchListBean;
import com.huxley.wii.wiibox.mvp.codekk.model.ResultBean;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.exception.EmptyException;
import com.thefinestartist.utils.log.L;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 *
 * Created by huxley on 16/7/31.
 */

public class CodekkPresenter implements CodekkContract.Presenter {

    private CodekkContract.View mView;
    private int page;
    private boolean isFirst;
    boolean hasMore;
    private int searchPage;
    private List<CodekkProjectBean> firstDatas = new ArrayList<>();
    private String searchContent = null;

    public CodekkPresenter (CodekkContract.View view){
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        page = 1;
        isFirst = true;
        hasMore = true;
        searchContent = null;
        loadCodekkList(page, true);
    }

    @Override
    public void loadMore(){
        if (!hasMore) {
            ToastHelper.showInfo("没有更多...");
            return;
        }
        if (searchContent != null) {
            search(searchContent, false);
        } else {
            loadCodekkList(page, false);
        }
    }

    @Override
    public void refresh(){
        start();
    }

    private void loadCodekkList(int page, boolean isRefresh){
        if (isFirst) {
            mView.setProgress(true);
        }
        CodekkModel.getInstance().getCodekkList(page)
                .subscribe(new Observer<ResultBean<CodekkHomeListBean>>() {
                    @Override
                    public void onCompleted() {
                        if (!isRefresh) {
                            CodekkPresenter.this.page ++;
                        }
                        if (isFirst) {
                            mView.setProgress(false);
                            isFirst = false;
                        }
                        L.i("completed = " + page);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isFirst) {
                            mView.setProgress(false);
                        }
                        if (ExceptionHelper.isNoNetException(e)) {
                            mView.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            mView.isEmptyView(isFirst);
                        }else {
                            mView.isErrorView(isFirst);
                        }
                        L.e(e);
                    }

                    @Override
                    public void onNext(ResultBean<CodekkHomeListBean> datas) {
                        if (datas == null || datas.code != 0) {
                            throw new RuntimeException();
                        }
                        CodekkHomeListBean data = datas.data;
                        if (data == null || data.isEmpty()) {
                            hasMore = false;
                            throw new EmptyException();
                        }
                        if (isFirst) {
                            firstDatas.addAll(data.projectArray);
                        }
                        mView.setContent(data.projectArray, isRefresh, isFirst);
                        L.json(datas.toString());
                    }
                });
    }

    @Override
    public void search(String query, boolean isFirst) {
        if (isFirst) {
            searchPage = 1;
            searchContent = query;
            hasMore = true;
        }
        CodekkModel.getInstance().getSearchList(query, searchPage)
                .subscribe(new Observer<ResultBean<CodekkSearchListBean>>() {
                    @Override
                    public void onCompleted() {
                        if (isFirst) {
                            CodekkPresenter.this.searchPage ++;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (isFirst) {
                            mView.setProgress(false);
                        }
                        if (ExceptionHelper.isNoNetException(e)) {
                            mView.isNoNetView(isFirst);
                        }else if (ExceptionHelper.isEmptyException(e)){
                            mView.isEmptyView(isFirst);
                        }else {
                            mView.isErrorView(isFirst);
                        }
                        L.e(e);
                    }
                    @Override
                    public void onNext(ResultBean<CodekkSearchListBean> datas) {

                        if (datas == null || datas.code != 0) {
                            throw new RuntimeException();
                        }
                        CodekkSearchListBean data = datas.data;
                        if (data == null || data.isEmpty()) {
                            hasMore = false;
                            throw new EmptyException();
                        }
                        mView.setContent(data.projectArray, isFirst, false);
                        L.json(datas.toString());
                    }
                });
    }

    @Override
    public void setFirstContent() {
        isFirst = true;
        hasMore = true;
        searchContent = null;
        mView.setContent(firstDatas, false, true);
    }
}
