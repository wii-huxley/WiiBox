package com.huxley.wii.wiitools.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.huxley.wii.wiitools.R;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by huxley on 16/7/21.
 */
public abstract class BaseListFragment<T> extends BaseNetFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerView mRecyclerView;
    private Handler mHandler;
    private Runnable mRunnable;
    protected List<T> mDatas = new ArrayList<>();
    protected RecyclerView.LayoutManager mLinearLayoutManager;
    protected CommonAdapter<T> mAdapter;

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        addView(R.layout.wii_layout_list);

        mSwipeRefreshLayout = $1(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.wii_color_500);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.wii_color_50);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = $1(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLinearLayoutManager = getLayoutManager());
        mRecyclerView.setAdapter(mAdapter = getAdapter());
    }


    protected void loadMore() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    protected abstract CommonAdapter<T> getAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected void showContentView(boolean isFirst) {
        if (isFirst) {
            super.showContentView();
        } else {
            dismissRefresh();
        }
    }

    protected void showEmptyView(boolean isFirst) {
        if (isFirst) {
            super.showEmptyView();
        } else {
            dismissRefresh();
        }
    }

    protected void showErrorView(boolean isFirst) {
        if (isFirst) {
            super.showErrorView();
        } else {
            dismissRefresh();
        }
    }

    protected void showNoNetView(boolean isFirst) {
        if (isFirst) {
            super.showNoNetView();
        } else {
            dismissRefresh();
        }
    }

    public void dismissRefresh(){
        if (mHandler == null) {
            mHandler = new Handler();
        }
        if (mRunnable == null) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            };
        }
        mHandler.postDelayed(mRunnable, 300);
    }
}
