package com.huxley.wii.wiibox.mvp.ting56;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiibox.mvp.ting56.search.Ting56SearchFragment;
import com.huxley.wii.wiibox.mvp.ting56.search.Ting56SearchPresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class Ting56Activity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Ting56SearchFragment mTing56SearchFragment;
    private Ting56SearchPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ting56;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
    }

    private void initView() {
        mPresenter = new Ting56SearchPresenter(mTing56SearchFragment = (Ting56SearchFragment)
                getSupportFragmentManager().findFragmentById(R.id.layout_ting56_search));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("听书网");
            toolbar.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            Ting56Activity.Adapter adapter = new Ting56Activity.Adapter(getSupportFragmentManager());

            Ting56Fragment militaryHistoryFragment = Ting56Fragment.newInstance();
            new Ting56Presenter(militaryHistoryFragment, Ting56Model.URL_MILITARY_HISTORY);
            adapter.addFragment(militaryHistoryFragment, "军事历史");

            Ting56Fragment forensicReasoningFragment = Ting56Fragment.newInstance();
            new Ting56Presenter(forensicReasoningFragment, Ting56Model.URL_FORENSIC_REASONING);
            adapter.addFragment(forensicReasoningFragment, "刑侦推理");

            Ting56Fragment workplaceMallFragment = Ting56Fragment.newInstance();
            new Ting56Presenter(workplaceMallFragment, Ting56Model.URL_WORKPLACE_MALL);
            adapter.addFragment(workplaceMallFragment, "职场商场");

            Ting56Fragment lectureRoomFragment = Ting56Fragment.newInstance();
            new Ting56Presenter(lectureRoomFragment, Ting56Model.URL_LECTURE_ROOM);
            adapter.addFragment(lectureRoomFragment, "百家讲坛");

            Ting56Fragment balladSingingFragment = Ting56Fragment.newInstance();
            new Ting56Presenter(balladSingingFragment, Ting56Model.URL_BALLAD_SINGING);
            adapter.addFragment(balladSingingFragment, "经典评书");

            Ting56Fragment humorousJokesFragment = Ting56Fragment.newInstance();
            new Ting56Presenter(humorousJokesFragment, Ting56Model.URL_HUMOROUS_JOKES);
            adapter.addFragment(humorousJokesFragment, "幽默笑话");

            viewPager.setAdapter(adapter);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
        
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mTing56SearchFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("输入小说名或相关关键字");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.search(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                viewPager.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(mTing56SearchFragment);
                transaction.commit();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(mTing56SearchFragment);
                transaction.commit();
                return true;
            }
        });
        return true;
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<BaseFragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        private Adapter(FragmentManager fm) {
            super(fm);
        }

        private void addFragment(BaseFragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public BaseFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
