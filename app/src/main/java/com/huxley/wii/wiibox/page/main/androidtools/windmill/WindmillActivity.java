package com.huxley.wii.wiibox.page.main.androidtools.windmill;

import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

/**
 * 风车 练习贝塞尔曲线
 * 数据点：通常指一条路径的起始点和终止点
 * 控制点：控制点决定了一条路径的弯曲轨迹，根据控制点的个数，贝塞尔曲线被分为一阶贝塞尔曲线（0个控制点）、
 *  二阶贝塞尔曲线（1个控制点）、三阶贝塞尔曲线（2个控制点）等等。
 */
public class WindmillActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        WindmillFragment mWindmillFragment = (WindmillFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (mWindmillFragment == null) {
            mWindmillFragment = WindmillFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mWindmillFragment, R.id.fragmentContent);
        }
        new WindmillPresenter(mWindmillFragment);
    }
}
