package com.huxley.wii.wiibox.mvp.dytt.search;

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
 * Created by huxley on 16/7/13.
 */
public class DyttSearchFragment extends BaseListFragment<DyttListBean.MovieInfo> implements DyttSearchContract.View {

    private DyttSearchContract.Presenter mPresenter;

    public static DyttSearchFragment newInstance() {
        return new DyttSearchFragment();
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
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
        return new LinearLayoutManager(getContext());
    }

    private void initView() {
        mSwipeRefreshLayout.setEnabled(false);
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((LinearLayoutManager) mLinearLayoutManager,
                mSwipeRefreshLayout, mAdapter, () -> mPresenter.loadMore()));
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
    public void onRefresh() {

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

    @Override
    public void setContent(boolean isRefresh, boolean isFirst, List<DyttListBean.MovieInfo> movieInfos) {
        super.showContentView(isFirst);
        if (isRefresh) {
            mDatas.clear();
        }
        mDatas.addAll(movieInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(DyttSearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
