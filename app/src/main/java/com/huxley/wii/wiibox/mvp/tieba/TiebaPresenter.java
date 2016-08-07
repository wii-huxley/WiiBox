package com.huxley.wii.wiibox.mvp.tieba;

import com.huxley.wii.wiibox.mvp.tieba.model.TiebaModel;
import com.huxley.wii.wiitools.common.Utils.L;

import rx.Subscriber;

/**
 * Created by huxley on 16/8/7.
 */

public class TiebaPresenter implements TiebaContract.Presenter {

    private TiebaContract.View tiebaView;

    public TiebaPresenter(TiebaContract.View view) {
        tiebaView = view;
        tiebaView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void getBarData(String barName) {
        TiebaModel.getInstance().getBarData(barName)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        L.i("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e(e);
                    }

                    @Override
                    public void onNext(String s) {
                        L.json(s);
                    }
                });
    }
}
