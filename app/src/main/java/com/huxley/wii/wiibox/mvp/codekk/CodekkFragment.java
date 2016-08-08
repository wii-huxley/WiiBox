package com.huxley.wii.wiibox.mvp.codekk;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiitools.base.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.Utils.DateUtils;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 *
 * Created by huxley on 16/7/31.
 */

public class CodekkFragment extends BaseRecyclerViewFragment<CodekkProjectBean> implements
        CodekkContract.View, SwipeRefreshLayout.OnRefreshListener {

    private CodekkContract.Presenter mPresenter;

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initListener();
    }

    private void initListener() {
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((StaggeredGridLayoutManager) mLayoutManager,
                mSwipeRefreshLayout, (CommonAdapter)mAdapter, this::loadMore));

        ((CommonAdapter)mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                UIHelper.openWebView(((CodekkProjectBean) o).codeKKUrl, ((CodekkProjectBean) o).projectName, getContext());
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return true;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setPresenter(CodekkContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new CommonAdapter<CodekkProjectBean>(getContext(), R.layout.item_codekk, mData) {
            @Override
            public void convert(ViewHolder holder, CodekkProjectBean codekkBean) {
                holder.setText(R.id.tvName, codekkBean.projectName);
                holder.setText(R.id.tvContent, codekkBean.desc);
                holder.setText(R.id.tvDate, DateUtils.formatCodekkDate(codekkBean.updateTime));
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
        return new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
    }

    protected void loadMore() {
        if (!mPresenter.hasMore()) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.loadMore();
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

    }

    @Override
    public void showContent(List<CodekkProjectBean> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}