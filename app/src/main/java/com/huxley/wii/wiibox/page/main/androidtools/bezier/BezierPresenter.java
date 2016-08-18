package com.huxley.wii.wiibox.page.main.androidtools.bezier;

import android.support.annotation.NonNull;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class BezierPresenter implements BezierContract.Presenter {

    private final BezierContract.View mBezierView;

    public BezierPresenter(@NonNull BezierContract.View view) {
        mBezierView = checkNotNull(view);
        mBezierView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
