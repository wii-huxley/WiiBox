package com.huxley.wii.wiibox.page.codekk.search;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.page.codekk.model.CodekkModel;
import com.huxley.wii.wiibox.page.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiibox.page.codekk.model.CodekkSearchListBean;
import com.huxley.wii.wiibox.page.codekk.model.ResultBean;
import com.huxley.wii.wiitools.common.Utils.L;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import java.util.List;

import rx.Subscriber;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class CodekkSearchPresenter implements CodekkSearchContract.Presenter {

    private final CodekkSearchContract.View mView;
    private       String                    searchContent;
    private       boolean                   hasMore;
    private       int                       nextPage;
    private       boolean                   errorState;

    public CodekkSearchPresenter(@NonNull CodekkSearchContract.View view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }


    @Override
    public void search(String query, boolean isFirst) {
        if (isFirst) {
            searchContent = query;
            nextPage = 1;
            hasMore = true;
        }
        CodekkModel.getInstance().getSearchList(query, nextPage)
                .subscribe(new Subscriber<ResultBean<CodekkSearchListBean>>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissLoading();
                        CodekkSearchPresenter.this.nextPage++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            mView.showNotNet();
                        } else {
                            mView.showError(e);
                            errorState = isFirst;
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
                            ToastHelper.showInfo(isFirst ? R.string.str_prompt_result_is_empty : R.string.str_prompt_no_more);
                            return;
                        }
                        mView.showContent(projectArray, isFirst);
                        L.json(datas.toString());
                    }
                });
    }

    @Override
    public void loadMore() {
        search(searchContent, false);
    }

    @Override
    public void reTry() {
        search(searchContent, errorState);
    }

    @Override
    public boolean hasMore() {
        return hasMore;
    }

    public void clearContent() {
        mView.clearContent();
    }
}
