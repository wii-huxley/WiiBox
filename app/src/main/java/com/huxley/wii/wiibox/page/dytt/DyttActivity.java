package com.huxley.wii.wiibox.page.dytt;

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
import com.huxley.wii.wiibox.page.dytt.model.DyttModel;
import com.huxley.wii.wiibox.page.dytt.search.DyttSearchFragment;
import com.huxley.wii.wiibox.page.dytt.search.DyttSearchPresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;
import com.huxley.wii.wiitools.common.helper.ResHelper;

import java.util.ArrayList;
import java.util.List;

public class DyttActivity extends BaseActivity {

    private ViewPager           viewPager;
    private TabLayout           tabLayout;
    private DyttSearchPresenter mDyttSearchPresenter;
    private FrameLayout         contentDyttSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dytt;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
    }

    private void initView() {
        contentDyttSearch = $(R.id.contentDyttSearch);
        DyttSearchFragment mDyttSearchFragment = (DyttSearchFragment) getSupportFragmentManager().findFragmentById(R.id.contentDyttSearch);
        if (mDyttSearchFragment == null) {
            mDyttSearchFragment = DyttSearchFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), mDyttSearchFragment, R.id.contentDyttSearch);
        }
        mDyttSearchPresenter = new DyttSearchPresenter(mDyttSearchFragment);
        contentDyttSearch.setVisibility(View.GONE);

        Toolbar toolbar = UIHelper.createToolbar(this, R.string.dytt_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        String[] titles = ResHelper.getStringArray(R.array.array_dytt_tab);
        String[] urls = ResHelper.getStringArray(R.array.array_dytt_url);

        viewPager = $(R.id.viewpager);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.createFragment(DyttModel.BaseUrl + urls[0], titles[0]);
        adapter.createFragment(DyttModel.BaseUrl + urls[1], titles[1]);
        adapter.createFragment(DyttModel.BaseUrl + urls[2], titles[2]);
        adapter.createFragment(DyttModel.BaseUrl + urls[3], titles[3]);
        adapter.createFragment(DyttModel.BaseUrl + urls[4], titles[4]);
        adapter.createFragment(DyttModel.BaseUrl + urls[5], titles[5]);
        adapter.createFragment(DyttModel.BaseUrl + urls[6], titles[6]);
        adapter.createFragment(DyttModel.BaseUrl + urls[7], titles[7]);
        adapter.createFragment(DyttModel.BaseUrl + urls[8], titles[8]);
        adapter.createFragment(DyttModel.BaseUrl + urls[9], titles[9]);
        viewPager.setAdapter(adapter);

        tabLayout = $(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(ResHelper.getString(R.string.dytt_hint_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mDyttSearchPresenter.search(query);
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
                contentDyttSearch.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                contentDyttSearch.setVisibility(View.VISIBLE);
                return true;
            }
        });
        return true;
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<BaseFragment> mFragments      = new ArrayList<>();
        private final List<String>       mFragmentTitles = new ArrayList<>();

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
            DyttFragment newMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(newMovieFragment, url);
            addFragment(newMovieFragment, title);
        }
    }
}
