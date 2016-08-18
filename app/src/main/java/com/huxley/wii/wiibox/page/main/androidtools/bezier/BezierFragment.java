package com.huxley.wii.wiibox.page.main.androidtools.bezier;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.huxley.wii.wiitools.base.BaseFragment;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class BezierFragment extends BaseFragment implements BezierContract.View {

    private BezierContract.Presenter mBezierPresenter;

    public static BezierFragment newInstance() {
        return new BezierFragment();
    }

    @Override
    public void setPresenter(@NonNull BezierContract.Presenter presenter) {
        mBezierPresenter = checkNotNull(presenter);
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
