package com.huxley.wii.wiibox.mvp.main.translate;

import com.huxley.wii.wiibox.mvp.main.translate.model.BaiduTranslateInfo;
import com.huxley.wii.wiibox.mvp.main.translate.model.TranslateModel;
import com.huxley.wii.wiibox.mvp.main.translate.model.YouDaoTranslateInfo;
import com.huxley.wii.wiitools.common.Utils.L;

import rx.Observer;
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
        TranslateModel.getInstance().baiduTranslateEn(content)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaiduTranslateInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaiduTranslateInfo baiduTranslateInfo) {
                        L.json(baiduTranslateInfo.toString());
                        translateView.setBaiduTranslateContent(baiduTranslateInfo);
                    }
                });
    }

    @Override
    public void youdaoTranslate(String content) {
        TranslateModel.getInstance().youdaoTranslate(content)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YouDaoTranslateInfo>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(YouDaoTranslateInfo youDaoTranslateInfo) {
                        L.json(youDaoTranslateInfo.toString());
                        translateView.setYoudaoTranslateContent(youDaoTranslateInfo);
                    }
                });
    }
}
