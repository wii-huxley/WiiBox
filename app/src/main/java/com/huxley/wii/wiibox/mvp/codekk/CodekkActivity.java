package com.huxley.wii.wiibox.mvp.codekk;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.SoftUtils;

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

        Toolbar toolbar = UIHelper.createToolbar(this);
        toolbar.setTitle(R.string.str_codekk);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        mPresenter = new CodekkPresenter((CodekkFragment) getSupportFragmentManager().findFragmentById(R.id.layout_codekk));
        mPresenter.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search title, tags, author, keywords, description etc.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.search(query, true);
                SoftUtils.hideSoftInput(CodekkActivity.this);
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
                mPresenter.resetContent();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mPresenter.clearContent();
                return true;
            }
        });
        return true;
    }
}