package com.huxley.wii.wiibox.mvp.tieba;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;


/**
 * Created by huxley on 16/8/7.
 */

public class TiebaFragment extends BaseFragment implements TiebaContract.View {

    private TiebaContract.Presenter tiebaPresenter;

    public static TiebaFragment newInstance() {
        return new TiebaFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tieba;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("输入小说名或相关关键字");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tiebaPresenter.getBarData(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });
    }

    @Override
    public void setPresenter(TiebaContract.Presenter presenter) {
        tiebaPresenter = presenter;
    }
}
