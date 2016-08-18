package com.huxley.wii.wiibox.page.main.androidtools.expandingPager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.widget.ExpandingPager.QsPagerTransformer;
import com.huxley.wii.wiibox.common.widget.ExpandingPager.Util;
import com.huxley.wii.wiibox.common.widget.ExpandingPager.fragments.QsContainFragment;
import com.huxley.wii.wiibox.page.main.androidtools.expandingPager.adapter.QsFragmentAdapter;
import com.huxley.wii.wiibox.page.main.androidtools.expandingPager.fragments.Fragment1;
import com.huxley.wii.wiibox.page.main.androidtools.expandingPager.fragments.Fragment2;
import com.huxley.wii.wiitools.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ExpandingPagerActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<QsContainFragment> lists = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_expanding_pager;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        setupWindowAnimations();
        viewPager = $(R.id.viewPager);
        viewPager.setPageTransformer(true, new QsPagerTransformer());
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        lists.add(QsContainFragment.getInstance(new Fragment1(), new Fragment2()));
        //add expanding listener
        for (QsContainFragment qs : lists) {
            qs.setOnExpandingClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //v is front view
                    startActivity(v.findViewById(R.id.img));
                }
            });
        }
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                QsContainFragment fragment = lists.get(viewPager.getCurrentItem());
                fragment.close();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new QsFragmentAdapter(getSupportFragmentManager(), lists));
        Util.setupPager(viewPager);
    }

    public void setupViewPager(ViewPager v) {

        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        ((ViewGroup) v.getParent()).setClipChildren(false);
        v.setClipChildren(false);
        layoutParams.width = getWindowManager().getDefaultDisplay().getWidth() / 7 * 5;
        layoutParams.height = (int) ((layoutParams.width / 0.75));

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode slideTransition = new Explode();
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    private void startActivity(View view) {
        Intent i = new Intent(this, InfoActivity.class);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair<>(view, "backimg"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(i, transitionActivityOptions.toBundle());
        } else {
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        if (!lists.get(viewPager.getCurrentItem()).isClosed()) {
            lists.get(viewPager.getCurrentItem()).close();
        } else {
            super.onBackPressed();
        }

    }
}
