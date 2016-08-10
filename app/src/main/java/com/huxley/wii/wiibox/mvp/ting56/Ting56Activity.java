package com.huxley.wii.wiibox.mvp.ting56;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.mvp.ting56.search.Ting56SearchFragment;
import com.huxley.wii.wiibox.mvp.ting56.search.Ting56SearchPresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;
import com.huxley.wii.wiitools.common.helper.ResHelper;

import java.util.ArrayList;
import java.util.List;

public class Ting56Activity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Ting56SearchPresenter mPresenter;
    private FrameLayout contentTing56Search;

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
        contentTing56Search = $(R.id.contentTing56Search);
        Ting56SearchFragment mTing56SearchFragment = (Ting56SearchFragment) getSupportFragmentManager().findFragmentById(R.id.contentTing56Search);
        if (mTing56SearchFragment == null) {
            mTing56SearchFragment = Ting56SearchFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mTing56SearchFragment, R.id.contentDyttSearch);
        }
        mPresenter = new Ting56SearchPresenter(mTing56SearchFragment);
        contentTing56Search.setVisibility(View.GONE);

        Toolbar toolbar = UIHelper.createToolbar(this);
        toolbar.setTitle(R.string.str_ting_56);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        String[] titles = ResHelper.getStringArray(R.array.array_ting56_tab);
        String[] urls = ResHelper.getStringArray(R.array.array_ting56_url);
        Ting56Activity.Adapter adapter = new Ting56Activity.Adapter(getSupportFragmentManager());
        adapter.createFragment(urls[0], titles[0]);
        adapter.createFragment(urls[1], titles[1]);
        adapter.createFragment(urls[2], titles[2]);
        adapter.createFragment(urls[3], titles[3]);
        adapter.createFragment(urls[4], titles[4]);
        adapter.createFragment(urls[5], titles[5]);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(ResHelper.getString(R.string.hint_ting56_search));
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
                contentTing56Search.setVisibility(View.GONE);
                return true;
            }
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                contentTing56Search.setVisibility(View.VISIBLE);
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

        private void createFragment(String url, String title) {
            Ting56Fragment humorousJokesFragment = Ting56Fragment.newInstance();
            new Ting56Presenter(humorousJokesFragment, url);
            addFragment(humorousJokesFragment, title);
        }
    }
}
