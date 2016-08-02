package com.huxley.wii.wiitools.listener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zhy.base.adapter.recyclerview.CommonAdapter;

/**
 * Created by huxley on 16/7/23.
 */
public class RecyclerViewScrollListener {


    private static int lastVisibleItem;
    private static boolean mIsFirstTimeTouchBottom = true;
    private static final int PRELOAD_SIZE = 2;

    public static RecyclerView.OnScrollListener getLoadMoreListener(final LinearLayoutManager layoutManager,
                                                                    final SwipeRefreshLayout swipeRefreshLayout,
                                                                    final CommonAdapter adapter,
                                                                    final OnLoadMoreListener loadMoreListener) {
        lastVisibleItem = 0;
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!swipeRefreshLayout.isRefreshing() && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    swipeRefreshLayout.setRefreshing(true);
                    loadMoreListener.loadMore();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem =layoutManager.findLastVisibleItemPosition();
            }
        };
    }

    public static RecyclerView.OnScrollListener getLoadMoreListener(final StaggeredGridLayoutManager layoutManager,
                                                                    final SwipeRefreshLayout swipeRefreshLayout,
                                                                    final CommonAdapter adapter,
                                                                    final OnLoadMoreListener loadMoreListener){
        lastVisibleItem = 0;
        return new RecyclerView.OnScrollListener(){
            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
                if (!swipeRefreshLayout.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >= adapter.getItemCount() - PRELOAD_SIZE) {
                    if (!mIsFirstTimeTouchBottom) {
                        swipeRefreshLayout.setRefreshing(true);
                        loadMoreListener.loadMore();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    public interface OnLoadMoreListener{
        void loadMore();
    }
}
