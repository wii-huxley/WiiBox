package com.huxley.wii.wiitools.base.interfaces;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

/**
 * IRecyclerView 接口
 * Created by LeiJin01 on 2016/8/5.
 */
public interface IRecyclerView {

    RecyclerView getRecyclerView();

    RecyclerView.Adapter getAdapter();

    SwipeRefreshLayout getSwipeRefreshLayout();

    RecyclerView.LayoutManager getLayoutManager();
}
