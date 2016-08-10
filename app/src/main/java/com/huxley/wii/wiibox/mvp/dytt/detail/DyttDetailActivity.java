package com.huxley.wii.wiibox.mvp.dytt.detail;

import android.content.Intent;
import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class DyttDetailActivity extends BaseActivity {

    private DyttListBean.MovieInfo mMovieInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dyttdetail;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        DyttDetailFragment dyttDetailFragment = (DyttDetailFragment) getSupportFragmentManager().findFragmentById(R.id.dyttDetailContent);
        if (dyttDetailFragment == null) {
            dyttDetailFragment = DyttDetailFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), dyttDetailFragment, R.id.dyttDetailContent);
        }

        new DyttDetailPresenter(dyttDetailFragment, mMovieInfo);
    }

    @Override
    protected void handleIntent(Intent intent) {
        mMovieInfo = (DyttListBean.MovieInfo) intent.getSerializableExtra(Constant.Key.DATE);
    }
}
