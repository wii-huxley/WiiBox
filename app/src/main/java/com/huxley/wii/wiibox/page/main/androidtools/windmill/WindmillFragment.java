package com.huxley.wii.wiibox.page.main.androidtools.windmill;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.huxley.wii.wiitools.base.BaseFragment;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class WindmillFragment extends BaseFragment implements WindmillContract.View {

    private WindmillContract.Presenter mWindmillPresenter;

    public static WindmillFragment newInstance() {
        return new WindmillFragment();
    }

    @Override
    public void setPresenter(@NonNull WindmillContract.Presenter presenter) {
        mWindmillPresenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

    }
}
