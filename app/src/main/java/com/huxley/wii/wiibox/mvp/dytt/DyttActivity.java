package com.huxley.wii.wiibox.mvp.dytt;

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
import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;
import com.huxley.wii.wiibox.mvp.dytt.search.DyttSearchFragment;
import com.huxley.wii.wiibox.mvp.dytt.search.DyttSearchPresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class DyttActivity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DyttSearchFragment mDyttSearchFragment;
    private DyttSearchPresenter mDyttSearchPresenter;

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
        mDyttSearchPresenter = new DyttSearchPresenter(mDyttSearchFragment = (DyttSearchFragment)
                getSupportFragmentManager().findFragmentById(R.id.layout_dytt_search));

        Toolbar toolbar = $(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("电影天堂");
            toolbar.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        viewPager = $(R.id.viewpager);
        if (viewPager != null) {
            Adapter adapter = new Adapter(getSupportFragmentManager());

            DyttFragment newMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(newMovieFragment, DyttModel.NewMovieBaseUrl);
            adapter.addFragment(newMovieFragment, "最新电影");

            DyttFragment chinaMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(chinaMovieFragment, DyttModel.ChinaMovieBaseUrl);
            adapter.addFragment(chinaMovieFragment, "国内电影");

            DyttFragment oumeiMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(oumeiMovieFragment, DyttModel.OumeiMovieBaseUrl);
            adapter.addFragment(oumeiMovieFragment, "欧美电影");

            DyttFragment rihanMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(rihanMovieFragment, DyttModel.RihanMovieBaseUrl);
            adapter.addFragment(rihanMovieFragment, "日韩电影");

            DyttFragment hytvMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(hytvMovieFragment, DyttModel.HytvMovieBaseUrl);
            adapter.addFragment(hytvMovieFragment, "华语电视");

            DyttFragment rihantvMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(rihantvMovieFragment, DyttModel.RihantvMovieBaseUrl);
            adapter.addFragment(rihantvMovieFragment, "日韩电视");

            DyttFragment oumeitvMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(oumeitvMovieFragment, DyttModel.OumeitvMovieBaseUrl);
            adapter.addFragment(oumeitvMovieFragment, "欧美电视");

            DyttFragment zongyi2013MovieFragment = DyttFragment.newInstance();
            new DyttPresenter(zongyi2013MovieFragment, DyttModel.Zongyi2013MovieBaseUrl);
            adapter.addFragment(zongyi2013MovieFragment, "最新综艺");

            DyttFragment zongyi2009MovieFragment = DyttFragment.newInstance();
            new DyttPresenter(zongyi2009MovieFragment, DyttModel.Zongyi2009MovieBaseUrl);
            adapter.addFragment(zongyi2009MovieFragment, "旧版综艺");

            DyttFragment dongmanMovieFragment = DyttFragment.newInstance();
            new DyttPresenter(dongmanMovieFragment, DyttModel.DongmanMovieBaseUrl);
            adapter.addFragment(dongmanMovieFragment, "动漫资源");

            viewPager.setAdapter(adapter);
        }


        tabLayout = $(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mDyttSearchFragment);
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
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(mDyttSearchFragment);
                transaction.commit();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(mDyttSearchFragment);
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
