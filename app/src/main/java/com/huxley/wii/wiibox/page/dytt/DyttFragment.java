package com.huxley.wii.wiibox.page.dytt;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.page.dytt.model.DyttListBean;
import com.huxley.wii.wiitools.base.recyclerview.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 * DyttFragment
 * Created by huxley on 16/7/21.
 */
public class DyttFragment extends BaseRecyclerViewFragment<DyttListBean.MovieInfo> implements DyttContract.View, SwipeRefreshLayout.OnRefreshListener {

    private DyttContract.Presenter mPresenter;

    public static DyttFragment newInstance() {
        return new DyttFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wii_layout_list;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initListener();
        mPresenter.start();
    }

    private void initListener() {
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((LinearLayoutManager) mLayoutManager,
                mSwipeRefreshLayout, (CommonAdapter)mAdapter, mPresenter::loadMore));
        ((CommonAdapter)mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                UIHelper.startDyttDetailActivity(getActivity(), (DyttListBean.MovieInfo) o);
            }
            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new CommonAdapter<DyttListBean.MovieInfo>(getContext(), R.layout.item_movie, mData) {
            @Override
            public void convert(ViewHolder holder, DyttListBean.MovieInfo movieInfo) {
                holder.setText(R.id.tvName, movieInfo.name);
                holder.setText(R.id.tvContent, movieInfo.content);
                holder.setVisible(R.id.spacer, holder.getItemPosition() == getItemCount() - 1);
            }
        };
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return $(R.id.swipeRefreshLayout);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void setPresenter(DyttContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void showLoading() {
        setRefreshing(true);
    }

    @Override
    public void dismissLoading() {
        setRefreshing(false);
    }

    @Override
    public void showNotNet() {
        SnackbarHelper.showNoNetInfo(rootView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showLoadErrorInfo(mRecyclerView, mPresenter::reTry);
    }

    @Override
    public void showContent(List<DyttListBean.MovieInfo> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
