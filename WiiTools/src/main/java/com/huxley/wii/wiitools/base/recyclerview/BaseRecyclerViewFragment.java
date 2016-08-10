package com.huxley.wii.wiitools.base.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.huxley.wii.wiitools.R;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.Utils.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LeiJin01 on 2016/8/5.
 */
public abstract class BaseRecyclerViewFragment<D> extends BaseFragment implements IRecyclerView {

    protected RecyclerView               mRecyclerView;
    protected RecyclerView.Adapter       mAdapter;
    protected SwipeRefreshLayout         mSwipeRefreshLayout;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected Handler                    mHandler;
    protected Runnable                   openRunnable;
    protected Runnable                   closeRunnable;
    protected List<D>                    mData;

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        mData = new ArrayList<>();
        mRecyclerView = NonNull.checkNotNull(getRecyclerView(), "please init RecyclerView");
        mAdapter = NonNull.checkNotNull(getAdapter(), "please create adapter");
        mLayoutManager = NonNull.checkNotNull(getLayoutManager(), "please create layoutManager");
        mSwipeRefreshLayout = getSwipeRefreshLayout();

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.wii_color_500);
            mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.wii_color_50);
            mHandler = new Handler();
        }
    }

    protected void setRefreshing(boolean refreshing) {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        if (refreshing) {
            if (openRunnable == null) {
                openRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                };
            }
            mHandler.post(openRunnable);
        } else {
            if (closeRunnable == null) {
                closeRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                };
            }
            mHandler.postDelayed(closeRunnable, 1000);
        }
    }
}
