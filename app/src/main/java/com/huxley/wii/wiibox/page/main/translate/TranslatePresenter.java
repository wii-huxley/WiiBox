package com.huxley.wii.wiibox.page.main.translate;

import com.huxley.wii.wiibox.page.main.translate.model.TranslateModel;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/18.
 */
public class TranslatePresenter implements TranslateContract.Presenter {

    private TranslateContract.View translateView;

    public TranslatePresenter(TranslateContract.View translateView) {
        this.translateView = checkNotNull(translateView);
        translateView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void baiduTranslate(String content) {
        TranslateModel.getInstance().baiduTranslate(content)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        translateView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        translateView.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        translateView.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            translateView.showNotNet();
                        } else {
                            translateView.showError(e);
                        }
                    }

                    @Override
                    public void onNext(String content) {
                        translateView.showContent(content, true);
                    }
                });
    }

    @Override
    public void youdaoTranslate(String content) {
        TranslateModel.getInstance().youdaoTranslate(content)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        translateView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        translateView.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        translateView.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            translateView.showNotNet();
                        }
                        translateView.showError(e);
                    }
                    @Override
                    public void onNext(String content) {
                        translateView.showContent(content, true);
                    }
                });
    }
}
