package com.huxley.wii.wiibox.mvp.main.csdn;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.widget.WiiViewPager;
import com.huxley.wii.wiibox.common.widget.viewpagerIndicator.ViewPagerIndicator;
import com.huxley.wii.wiitools.base.BaseFragment;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class CsdnFragment extends BaseFragment {

    private ViewPagerIndicator mIndicator;
    private WiiViewPager wiiViewPager;
    public static final String[] TITLES = new String[] { "业界", "移动", "研发", "程序员杂志", "云计算" };

    public CsdnFragment() {
        // Required empty public constructor
    }

    public static CsdnFragment newInstance() {
        return new CsdnFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_csdn;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        mIndicator = $(R.id.indicator);
        wiiViewPager = $(R.id.wiiViewPager);

        mIndicator.setTabItemTitles(Arrays.asList(TITLES));
        mIndicator.setViewPager(wiiViewPager, 0);
    }
}
