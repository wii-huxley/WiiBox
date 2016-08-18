package com.huxley.wii.wiibox.page.main.androidtools.windmill;

import android.support.annotation.NonNull;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class WindmillPresenter implements WindmillContract.Presenter {

    private final WindmillContract.View mWindmillView;

    public WindmillPresenter(@NonNull WindmillContract.View view) {
        mWindmillView = checkNotNull(view);
        mWindmillView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
