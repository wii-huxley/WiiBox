package com.huxley.wii.wiibox.page.codekk.detail;

import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.page.codekk.model.CodekkModel;
import com.huxley.wii.wiibox.page.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiibox.page.codekk.model.ResultBean;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import rx.Subscriber;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class CodekkDetailPresenter implements CodekkDetailContract.Presenter {

    private final CodekkDetailContract.View mCodekkDetailView;
    private       String                    id;

    public CodekkDetailPresenter(@NonNull CodekkDetailContract.View view, String id) {
        mCodekkDetailView = checkNotNull(view);
        this.id = id;
        mCodekkDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        CodekkModel.getInstance().getDetailInfo(id)
                .subscribe(new Subscriber<ResultBean<CodekkProjectBean>>() {
                    @Override
                    public void onStart() {
                        mCodekkDetailView.showLoading();
                    }
                    @Override
                    public void onNext(ResultBean<CodekkProjectBean> codekkProjectBeanResultBean) {
                        mCodekkDetailView.showContent(codekkProjectBeanResultBean.data.content, false);
                    }
                    @Override
                    public void onCompleted() {
                        mCodekkDetailView.dismissLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCodekkDetailView.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            mCodekkDetailView.showNotNet();
                        } else {
                            mCodekkDetailView.showError(e);
                        }
                    }
                });
    }
}
