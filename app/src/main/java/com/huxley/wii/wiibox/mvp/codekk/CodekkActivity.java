package com.huxley.wii.wiibox.mvp.codekk;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseActivity;

/**
 *
 * Created by huxley on 16/7/31.
 */
public class CodekkActivity extends BaseActivity {

    private CodekkPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_codekk;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        init();
    }

    private void init() {
        Toolbar toolbar = $(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Codekk");
            toolbar.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        mPresenter = new CodekkPresenter((CodekkFragment) getSupportFragmentManager().findFragmentById(R.id.layout_codekk));
        mPresenter.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search title, tags, author, keywords, description etc.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.search(query, true);
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
                mPresenter.setFirstContent();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });
        return true;
    }
}