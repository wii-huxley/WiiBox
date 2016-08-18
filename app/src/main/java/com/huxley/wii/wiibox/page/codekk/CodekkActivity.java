package com.huxley.wii.wiibox.page.codekk;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.page.codekk.search.CodekkSearchFragment;
import com.huxley.wii.wiibox.page.codekk.search.CodekkSearchPresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;
import com.huxley.wii.wiitools.common.helper.ResHelper;
import com.huxley.wii.wiitools.common.helper.SoftUtils;

/**
 * Created by huxley on 16/7/31.
 */
public class CodekkActivity extends BaseActivity {

    private CodekkSearchPresenter mCodekkSearchPresenter;
    private FrameLayout           fragmentContent;
    private FrameLayout           fragmentSearchContent;

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

        Toolbar toolbar = UIHelper.createToolbar(this, R.string.codekk_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        fragmentContent = $(R.id.fragmentContent);
        fragmentSearchContent = $(R.id.fragmentSearchContent);

        CodekkFragment codekkFragment = (CodekkFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContent);
        if (codekkFragment == null) {
            codekkFragment = CodekkFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), codekkFragment, R.id.fragmentContent);
        }
        new CodekkPresenter(codekkFragment);

        CodekkSearchFragment codekkSearchFragment = (CodekkSearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSearchContent);
        if (codekkSearchFragment == null) {
            codekkSearchFragment = CodekkSearchFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), codekkSearchFragment, R.id.fragmentSearchContent);
        }
        mCodekkSearchPresenter = new CodekkSearchPresenter(codekkSearchFragment);
        fragmentSearchContent.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(ResHelper.getString(R.string.codekk_hint_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mCodekkSearchPresenter.search(query, true);
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
                fragmentSearchContent.setVisibility(View.GONE);
                fragmentContent.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                fragmentSearchContent.setVisibility(View.VISIBLE);
                fragmentContent.setVisibility(View.GONE);
                mCodekkSearchPresenter.clearContent();
                return true;
            }
        });
        return true;
    }
}