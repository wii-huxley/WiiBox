package com.huxley.wii.wiibox.mvp.ting56.detail;

import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;
import com.huxley.wii.wiitools.exception.EmptyException;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/13.
 */
public class TingPlayPresenter implements TingPlayContract.Presenter{

    private final TingPlayContract.View tingPlayView;

    public TingPlayPresenter(TingPlayContract.View tingPlayView) {
        this.tingPlayView = checkNotNull(tingPlayView);
        this.tingPlayView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void openBook(String content) {
        tingPlayView.setProgress(true);
        Ting56Model.getInstance().getChapterInfos(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Ting56Bean.Ting56DetailBean>>() {
                    @Override
                    public void onCompleted() {
                        tingPlayView.setProgress(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        tingPlayView.setProgress(false);
                        if (ExceptionHelper.isNoNetException(e)) {
                            tingPlayView.isNoNetView();
                        }else if (ExceptionHelper.isEmptyException(e)){
                            tingPlayView.isEmptyView();
                        }else {
                            tingPlayView.isErrorView();
                        }
                    }
                    @Override
                    public void onNext(List<Ting56Bean.Ting56DetailBean> ting56DetailBeen) {
                        if (ting56DetailBeen == null || ting56DetailBeen.size() <= 0) {
                            throw new EmptyException();
                        }
                        tingPlayView.setContent(ting56DetailBeen);
                    }
                });
    }
}
