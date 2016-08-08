package com.huxley.wii.wiibox.mvp.dytt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiitools.base.BaseListFragment;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 *
 * Created by huxley on 16/7/21.
 */
public class DyttFragment extends BaseListFragment <DyttListBean.MovieInfo>implements DyttContract.View {

    private DyttContract.Presenter mPresenter;

    public static DyttFragment newInstance() {
        return new DyttFragment();
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        mPresenter.start();
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((LinearLayoutManager) mLinearLayoutManager,
                mSwipeRefreshLayout, mAdapter, mPresenter::loadMore));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                UIHelper.startDyttDetailActivity(getActivity(), (DyttListBean.MovieInfo)o);
            }
            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    @Override
    protected CommonAdapter<DyttListBean.MovieInfo> getAdapter() {
        return new CommonAdapter<DyttListBean.MovieInfo>(getContext(), R.layout.item_movie, mDatas) {
            @Override
            public void convert(ViewHolder holder, DyttListBean.MovieInfo movieInfo) {
                holder.setText(R.id.tvName, movieInfo.name);
                holder.setText(R.id.tvContent, movieInfo.content);
                holder.setVisible(R.id.spacer, holder.getItemPosition() == getItemCount() - 1);
            }
        };
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void setPresenter(DyttContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void loadMore() {
        super.loadMore();
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void setContent(List<DyttListBean.MovieInfo> movieInfos, boolean isRefresh, boolean isFirst) {
        super.showContentView(isFirst);
        if (isRefresh) {
            mDatas.clear();
        }
        mDatas.addAll(movieInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void isNoNetView(boolean isFirst) {
        super.showNoNetView(isFirst);
        if (!isFirst) {
            ToastHelper.showInfo("没有网络");
        }
    }

    @Override
    public void isEmptyView(boolean isFirst) {
        super.showEmptyView(isFirst);
        if (!isFirst) {
            ToastHelper.showInfo("没有内容");
        }
    }

    @Override
    public void isErrorView(boolean isFirst) {
        super.showErrorView(isFirst);
        if (!isFirst) {
            ToastHelper.showInfo("加载错误");
        }
    }

    @Override
    public void setProgress(boolean isShow) {
        if (isShow) {
            super.showProgress();
        } else {
            super.dismissProgress();
        }
    }

    @Override
    protected void isErrorToLoad() {
        super.isErrorToLoad();
        mPresenter.start();
    }
}
